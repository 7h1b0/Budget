package com.th1b0.budget.features.transactionform;

import android.support.annotation.NonNull;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Container;
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

  @Override public void loadCategoriesAndContainers() {
    mSubscription.add(mDataManager.getContainers()
        .map(containers -> {
          containers.add(0, new Container(Container.NONE, getView().getContext().getString(R.string.none), 0));
          return containers;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(containers -> {
          if (isViewAttached()) {
            getView().onContainersLoaded(containers);
          }
        })
        .observeOn(Schedulers.io())
        .filter(ignored -> isViewAttached())
        .flatMap(ignored -> mDataManager.getCategories())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(categories -> {
          if (isViewAttached()) {
            getView().onCategoriesLoaded(categories);
          }
        }, error -> {
          if (isViewAttached()) {
            getView().onError(error.getMessage());
          }
        }));
  }
}
