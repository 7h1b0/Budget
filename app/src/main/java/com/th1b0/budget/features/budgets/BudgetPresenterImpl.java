package com.th1b0.budget.features.budgets;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class BudgetPresenterImpl extends PresenterImpl<BudgetView>
    implements BudgetPresenter {

  BudgetPresenterImpl(@NonNull DataManager dataManager) {
    super(dataManager);
  }

  @Override public void loadBudgets() {
    mSubscription.add(mDataManager.getBudgets()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(budgets -> {
          BudgetView view = getView();
          if (view != null) {
            view.onBudgetsLoaded(budgets);
          }}, error -> {
          BudgetView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }

  @Override public void deleteBudget(@NonNull Budget budget) {
    mSubscription.add(mDataManager.deleteBudget(budget)
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe());
  }
}
