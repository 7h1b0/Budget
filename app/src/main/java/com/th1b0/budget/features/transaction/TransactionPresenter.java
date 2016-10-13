package com.th1b0.budget.features.transaction;

import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface TransactionPresenter extends Presenter {
  void loadTransaction();

  void loadTransaction(int month, int year);

  void deleteTransaction(Transaction transaction);
}
