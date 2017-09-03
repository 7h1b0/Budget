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
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
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

  public Single<Integer> updateTransaction(@NonNull final Transaction transaction) {
    return Single.fromCallable(() -> mTransactionTable.update(transaction));
  }

  public Single<Long> addTransaction(@NonNull final Transaction transaction) {
    return Single.fromCallable(() -> mTransactionTable.add(transaction));
  }

  public Completable deleteTransaction(@NonNull final Transaction transaction) {
    return Completable.fromAction(() -> mTransactionTable.delete(transaction));
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

  public Single<Long> addCategory(@NonNull final Category category) {
    return Single.fromCallable(() -> mCategoryTable.add(category));
  }

  public Single<Integer> updateCategory(@NonNull final Category category) {
    return Single.fromCallable(() -> mCategoryTable.update(category));
  }

  public Maybe<Integer> deleteCategory(@NonNull Category category) {
    return Single.fromCallable(() -> mCategoryTable.delete(category))
        .filter(rows -> rows > 0)
        .map(ignored -> mTransactionTable.delete(category));
  }

  public Observable<ArrayList<Budget>> getBudgets() {
    return mBudgetTable.getAll();
  }

  public Single<Long> addBudget(@NonNull final Budget budget) {
    return Single.fromCallable(() -> mBudgetTable.add(budget));
  }

  public Single<Integer> updateBudget(@NonNull final Budget budget) {
    return Single.fromCallable(() -> mBudgetTable.update(budget));
  }

  public Maybe<Long> deleteBudget(@NonNull Budget budget) {
    return Single.fromCallable(() -> mBudgetTable.delete(budget))
        .filter(rows -> rows > 0)
        .map(ignored -> budget.getId())
        .doOnSuccess(mCategoryTable::removeIdBudget)
        .doOnSuccess(mTransactionTable::removeIdBudget);
  }

  public void initializeDatabase(ArrayList<Budget> budgets, ArrayList<Category> categories) {
    mQueryUtil.initializeDatabase(budgets, categories);
  }

  public Observable<Boolean> isDatabaseEmpty() {
    return Observable.combineLatest(mCategoryTable.isEmpty(), mTransactionTable.isEmpty(),
        (isCategoryEmpty, isTransactionEmpty) -> isCategoryEmpty != null
            && isCategoryEmpty
            && isTransactionEmpty != null
            && isTransactionEmpty);
  }
}
