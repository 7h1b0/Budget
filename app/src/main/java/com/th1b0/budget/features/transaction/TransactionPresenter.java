package com.th1b0.budget.features.transaction;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.BasePresenter;

/**
 * Created by 7h1b0.
 */

interface TransactionPresenter extends BasePresenter<TransactionView> {
  void loadTransaction();

  void loadTransaction(int year, int month, long idBudget);

  void loadTransaction(int year, int month);

  void deleteTransaction(@NonNull Transaction transaction);
}
