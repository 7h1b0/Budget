package com.th1b0.budget.features.budgetform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class BudgetFormPresenterImpl extends PresenterImpl<BudgetFormView> implements
    BudgetFormPresenter {

  BudgetFormPresenterImpl(@NonNull final DataManager dataManager) {
    super(dataManager);
  }

  @Override public void addBudget(@NonNull final Budget budget) {
    mSubscription.add(mDataManager.addBudget(budget)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(id -> {
          BudgetFormView view = getView();
          if (view != null) {
            view.onAddSucceeded();
          }
        }, err -> {
          BudgetFormView view = getView();
          if (view != null) {
            view.onError(err.getMessage());
          }
        }));
  }

  @Override public void updateBudget(@NonNull final Budget budget) {
    mSubscription.add(mDataManager.updateBudget(budget)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(id -> {
          BudgetFormView view = getView();
          if (view != null) {
            view.onUpdateSucceeded();
          }
        }, err -> {
          BudgetFormView view = getView();
          if (view != null) {
            view.onError(err.getMessage());
          }
        }));
  }
}
