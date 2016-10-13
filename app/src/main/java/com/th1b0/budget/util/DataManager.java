package com.th1b0.budget.util;

import android.content.Context;
import android.support.annotation.NonNull;
import com.th1b0.budget.database.BudgetTable;
import com.th1b0.budget.database.TransactionTable;
import com.th1b0.budget.model.Budget;
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
  private BehaviorSubject<String> mRxPreferences;

  private DataManager(@NonNull Context context) {
    mTransactionTable = new TransactionTable(context);
    mBudgetTable = new BudgetTable(context);
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

  public void updateTransaction(Transaction transaction) {
    mTransactionTable.update(transaction);
  }

  public long addTransaction(Transaction transaction) {
    return mTransactionTable.add(transaction);
  }

  public void deleteTransaction(Transaction transaction) {
    mTransactionTable.delete(transaction);
  }

  public Observable<ArrayList<Budget>> getBudgets(int limit) {
    return mBudgetTable.getAll(limit);
  }

  public void preferenceChange(String keyPref) {
    mRxPreferences.onNext(keyPref);
  }

  public Observable<String> onPreferenceChange(String... keys) {
    List<String> keysList = Arrays.asList(keys);
    return mRxPreferences.filter(keysList::contains);
  }
}
