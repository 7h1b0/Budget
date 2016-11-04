package com.th1b0.budget.util;

import android.content.Context;
import android.support.annotation.NonNull;
import com.th1b0.budget.database.BudgetTable;
import com.th1b0.budget.database.CategoryTable;
import com.th1b0.budget.database.ContainerTable;
import com.th1b0.budget.database.TransactionTable;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.model.PresentationBudget;
import com.th1b0.budget.model.PresentationHistory;
import com.th1b0.budget.model.Transaction;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by 7h1b0.
 */

public class DataManager {

  private static DataManager sInstance;
  private TransactionTable mTransactionTable;
  private BudgetTable mBudgetTable;
  private CategoryTable mCategoryTable;
  private ContainerTable mContainerTable;

  private DataManager(@NonNull Context context) {
    mTransactionTable = new TransactionTable(context);
    mBudgetTable = new BudgetTable(context);
    mCategoryTable = new CategoryTable(context);
    mContainerTable = new ContainerTable(context);
  }

  public static DataManager getInstance(@NonNull Context context) {
    if (sInstance == null) {
      sInstance = new DataManager(context);
    }
    return sInstance;
  }

  public Observable<ArrayList<Transaction>> getTransactions(int limit) {
    return mTransactionTable.getAll(limit);
  }

  public Observable<ArrayList<Transaction>> getTransactions(int month, int year) {
    return mTransactionTable.getAll(month, year);
  }

  public void updateTransaction(@NonNull Transaction transaction) {
    mTransactionTable.update(transaction);
  }

  public long addTransaction(@NonNull Transaction transaction) {
    return mTransactionTable.add(transaction);
  }

  public int deleteTransaction(@NonNull Transaction transaction) {
    return mTransactionTable.delete(transaction);
  }

  public Observable<ArrayList<PresentationBudget>> getBudgets(int month, int year) {
    return mBudgetTable.getAll(month, year);
  }

  public Observable<ArrayList<PresentationHistory>> getHistory() {
    return mBudgetTable.getHistory();
  }

  public Observable<ArrayList<Category>> getCategories() {
    return mCategoryTable.getAll();
  }

  public long addCategory(@NonNull Category category) {
    return mCategoryTable.add(category);
  }

  public int updateCategory(@NonNull Category category) {
    return mCategoryTable.update(category);
  }

  public Observable<Integer> deleteCategory(@NonNull Category category) {
    return Observable.just(mCategoryTable.delete(category))
        .filter(rows -> rows > 0)
        .map(ignored -> mTransactionTable.delete(category));
  }

  public Observable<ArrayList<Container>> getContainers(){
    return mContainerTable.getAll();
  }

  public long addContainers(@NonNull Container container) {
    return mContainerTable.add(container);
  }

  public int updateContainer(@NonNull Container container) {
    return mContainerTable.update(container);
  }

  public Observable<Void> deleteContainer(@NonNull Container container) {
    return Observable.just(container)
        .map(mContainerTable::delete)
        .filter(rows -> rows > 0)
        .map(ignored -> container.getId())
        .doOnNext(mCategoryTable::removeIdContainer)
        .doOnNext(mTransactionTable::removeIdContainer)
        .flatMap(ignored -> Observable.empty());

  }

  public void initializeDatabase(ArrayList<Container> containers, ArrayList<Category> categories) {
    mBudgetTable.initializeDatabase(containers, categories);
  }
}
