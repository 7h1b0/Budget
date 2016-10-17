package com.th1b0.budget.util;

import android.content.Context;
import android.support.annotation.NonNull;
import com.th1b0.budget.database.BudgetTable;
import com.th1b0.budget.database.CategoryTable;
import com.th1b0.budget.database.ContainerTable;
import com.th1b0.budget.database.TransactionTable;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.model.Transaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by 7h1b0.
 */

public class DataManager {

  private static DataManager mInstance;
  private TransactionTable mTransactionTable;
  private BudgetTable mBudgetTable;
  private CategoryTable mCategoryTable;
  private ContainerTable mContainerTable;
  private BehaviorSubject<String> mRxPreferences;

  private DataManager(@NonNull Context context) {
    mTransactionTable = new TransactionTable(context);
    mBudgetTable = new BudgetTable(context);
    mCategoryTable = new CategoryTable(context);
    mContainerTable = new ContainerTable(context);
    mRxPreferences = BehaviorSubject.create(Preferences.START);
  }

  public static DataManager getInstance(@NonNull Context context) {
    if (mInstance == null) {
      mInstance = new DataManager(context);
    }
    return mInstance;
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

  public Observable<ArrayList<Budget>> getBudgets(int limit) {
    return mBudgetTable.getAll(limit);
  }

  public Observable<ArrayList<Category>> getCategories() {
    return mCategoryTable.getAll();
  }

  public long addCategory(@NonNull Category category) {
    return mCategoryTable.add(category);
  }

  public void addCategories(ArrayList<Category> categories) {
    mCategoryTable.add(categories);
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

  /** TODO Update Category where idContainer = container.getId() */
  public int deleteContainer(@NonNull Container container) {
    return mContainerTable.delete(container);
  }

  public void preferenceChange(String keyPref) {
    mRxPreferences.onNext(keyPref);
  }

  public Observable<String> onPreferenceChange(String... keys) {
    List<String> keysList = Arrays.asList(keys);
    return mRxPreferences.filter(keysList::contains);
  }
}
