package com.th1b0.budget.features.wizard;

import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;

/**
 * Created by 7h1b0.
 */

final class WizardTransactionPresenterImpl extends PresenterImpl<Object>
    implements WizardTransactionPresenter {

  WizardTransactionPresenterImpl(Object view, DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void addTransaction(Transaction transaction) {
    mDataManager.addTransaction(transaction);
  }

  @Override public void updateTransaction(Transaction transaction) {
    mDataManager.updateTransaction(transaction);
  }
}
