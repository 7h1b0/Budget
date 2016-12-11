package com.th1b0.budget.util;

import android.content.Context;
import android.support.annotation.NonNull;
import com.th1b0.budget.database.BudgetTable;
import com.th1b0.budget.database.CategoryTable;
import com.th1b0.budget.database.QueryUtil;
import com.th1b0.budget.database.TransactionTable;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.PresentationBudget;
import com.th1b0.budget.model.PresentationHistory;
import com.th1b0.budget.model.Transaction;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by 7h1b0.
 */

public final class DataManager {

  private static DataManager sInstance;
  private TransactionTable mTransactionTable;
  private QueryUtil mQueryUtil;
  private CategoryTable mCategoryTable;
  private BudgetTable mBudgetTable;

  private DataManager(@NonNull Context context) {
    mTransactionTable = new TransactionTable(context);
    mQueryUtil = new QueryUtil(context);
    mCategoryTable = new CategoryTable(context);
    mBudgetTable = new BudgetTable(context);
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

  public Observable<ArrayList<Transaction>> getTransactions(int year, int month) {
    return mTransactionTable.getAll(year, month);
  }

  public Observable<ArrayList<Transaction>> getTransactions(int year, int month, long idBudget) {
    return mTransactionTable.getAll(year, month, idBudget);
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
    return mQueryUtil.getAll(month, year);
  }

  public Observable<ArrayList<PresentationHistory>> getHistory() {
    return mQueryUtil.getHistory();
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

  public Observable<ArrayList<Budget>> getBudgets() {
    return mBudgetTable.getAll();
  }

  public long addBudget(@NonNull Budget budget) {
    return mBudgetTable.add(budget);
  }

  public int updateBudget(@NonNull Budget budget) {
    return mBudgetTable.update(budget);
  }

  public Observable<Void> deleteBudget(@NonNull Budget budget) {
    return Observable.just(budget)
        .map(mBudgetTable::delete)
        .filter(rows -> rows > 0)
        .map(ignored -> budget.getId())
        .doOnNext(mCategoryTable::removeIdBudget)
        .doOnNext(mTransactionTable::removeIdBudget)
        .flatMap(ignored -> Observable.empty());
  }

  public void initializeDatabase(ArrayList<Budget> budgets, ArrayList<Category> categories) {
    mQueryUtil.initializeDatabase(budgets, categories);
  }

  public Observable<Boolean> isDatabaseEmpty() {
    return Observable.combineLatest(mCategoryTable.isEmpty(), mTransactionTable.isEmpty(),
        (isCategoryEmpty, isTransactionEmpty) -> isCategoryEmpty != null && isCategoryEmpty && isTransactionEmpty != null && isTransactionEmpty);
  }
}
