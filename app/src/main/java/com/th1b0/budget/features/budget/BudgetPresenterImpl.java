package com.th1b0.budget.features.budget;

import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;
import com.th1b0.budget.util.Preferences;
import com.th1b0.budget.util.PresenterImpl;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class BudgetPresenterImpl extends PresenterImpl<BudgetView> implements BudgetPresenter {

  BudgetPresenterImpl(BudgetView view, DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void loadBudgets() {
    mSubscription.add(Observable.combineLatest(mDataManager.getBudgets(12),
        mDataManager.onPreferenceChange(Preferences.PREF_BUDGET_VALUE, Preferences.START,
            Preferences.PREF_ENLARGE_FIRST_CELL), (budgets, onChange) -> {
          double goal = Preferences.getBudgetValue(mView.getContext());
          for (Budget budget : budgets) {
            budget.setDate(DateUtil.formatDate(budget.getYear(), budget.getMonth()));
            budget.setGoal(goal);
          }
          return budgets;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mView::onBudgetLoaded, error -> mView.onError(error.getMessage())));
  }
}
