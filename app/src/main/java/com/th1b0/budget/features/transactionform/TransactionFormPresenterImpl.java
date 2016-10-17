package com.th1b0.budget.features.transactionform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class TransactionFormPresenterImpl extends PresenterImpl<TransactionFormView>
    implements TransactionFormPresenter {

  TransactionFormPresenterImpl(TransactionFormView view, DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void addTransaction(@NonNull Transaction transaction) {
    mDataManager.addTransaction(transaction);
  }

  @Override public void updateTransaction(@NonNull Transaction transaction) {
    mDataManager.updateTransaction(transaction);
  }

  @Override public void loadCategory() {
    mSubscription.add(mDataManager.getCategories()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mView::onCategoriesLoaded, error -> mView.onError(error.getMessage())));
  }
}
