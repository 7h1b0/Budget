package com.th1b0.budget.features.wizard;

import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface WizardTransactionPresenter extends Presenter {

  void addTransaction(Transaction transaction);

  void updateTransaction(Transaction transaction);
}
