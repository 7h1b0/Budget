package com.th1b0.budget.features.transactionform;

import android.support.annotation.NonNull;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Budget;
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

  TransactionFormPresenterImpl(@NonNull final DataManager dataManager) {
    super(dataManager);
  }

  @Override public void addTransaction(@NonNull final Transaction transaction) {
    mSubscription.add(mDataManager.addTransaction(transaction)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(id -> {
          TransactionFormView view = getView();
          if (view != null) {
            view.onAddSucceeded();
          }
        }, err -> {
          TransactionFormView view = getView();
          if (view != null) {
            view.onError(err.getMessage());
          }
        }));
  }

  @Override public void updateTransaction(@NonNull final Transaction transaction) {
    mSubscription.add(mDataManager.updateTransaction(transaction)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(id -> {
          TransactionFormView view = getView();
          if (view != null) {
            view.onUpdateSucceeded();
          }
        }, err -> {
          TransactionFormView view = getView();
          if (view != null) {
            view.onError(err.getMessage());
          }
        }));
  }

  @Override public void loadCategoriesAndBudgets() {
    mSubscription.add(mDataManager.getBudgets()
        .map(budgets -> {
          TransactionFormView view = getView();
          if (view != null) {
            budgets.add(0,
                new Budget(Budget.NONE, getView().getContext().getString(R.string.none), 0));
            return budgets;
          }
          return null;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnNext(budgets -> {
          TransactionFormView view = getView();
          if (view != null) {
            view.onBudgetsLoaded(budgets);
          }
        })
        .observeOn(Schedulers.io())
        .flatMap(ignored -> mDataManager.getCategories())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(categories -> {
          TransactionFormView view = getView();
          if (view != null) {
            view.onCategoriesLoaded(categories);
          }
        }, error -> {
          TransactionFormView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }
}
