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
import io.reactivex.Observable;
import java.util.ArrayList;

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

  public static synchronized DataManager getInstance(@NonNull Context context) {
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

  public Observable<Integer> updateTransaction(@NonNull final Transaction transaction) {
    return Observable.just(transaction).map(mTransactionTable::update);
  }

  public Observable<Long> addTransaction(@NonNull final Transaction transaction) {
    return Observable.just(transaction).map(mTransactionTable::add);
  }

  public Observable<Integer> deleteTransaction(@NonNull final Transaction transaction) {
    return Observable.just(transaction).map(mTransactionTable::delete);
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

  public Observable<Long> addCategory(@NonNull final Category category) {
    return Observable.just(category).map(mCategoryTable::add);
  }

  public Observable<Integer> updateCategory(@NonNull final Category category) {
    return Observable.just(category).map(mCategoryTable::update);
  }

  public Observable<Integer> deleteCategory(@NonNull Category category) {
    return Observable.just(category)
        .map(mCategoryTable::delete)
        .filter(rows -> rows > 0)
        .map(ignored -> mTransactionTable.delete(category));
  }

  public Observable<ArrayList<Budget>> getBudgets() {
    return mBudgetTable.getAll();
  }

  public Observable<Long> addBudget(@NonNull final Budget budget) {
    return Observable.just(budget).map(mBudgetTable::add);
  }

  public Observable<Integer> updateBudget(@NonNull final Budget budget) {
    return Observable.just(budget).map(mBudgetTable::update);
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
