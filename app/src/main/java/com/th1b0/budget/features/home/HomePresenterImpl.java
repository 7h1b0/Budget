package com.th1b0.budget.features.home;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;
import com.th1b0.budget.util.Preferences;
import com.th1b0.budget.util.PresenterImpl;
import java.util.ArrayList;
import java.util.Calendar;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class HomePresenterImpl extends PresenterImpl<HomeView> implements homePresenter {

  HomePresenterImpl(@NonNull HomeView view, @NonNull DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void loadBudgets() {
    mSubscription.add(Observable.combineLatest(mDataManager.getBudgets(12),
        mDataManager.onPreferenceChange(Preferences.PREF_BUDGET_VALUE, Preferences.START,
            Preferences.PREF_ENLARGE_FIRST_CELL), (budgets, onChange) -> {
          double goal = Preferences.getBudgetValue(mView.getContext());
          return getBudgets(budgets, goal, 12);
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mView::onBudgetLoaded, error -> mView.onError(error.getMessage())));
  }

  private boolean isAtTheSameDate(long date, @NonNull Budget budget) {
    long budgetDate = DateUtil.getTimestamp(budget.getYear(), budget.getMonth(), 1);
    return date == budgetDate;
  }

  private ArrayList<Budget> getBudgets(@NonNull ArrayList<Budget> budgets, double goal, int limit) {
    if (budgets.isEmpty()) {
      return budgets;
    }

    long date = DateUtil.getTimestamp(DateUtil.getCurrentYear(), DateUtil.getCurrentMonth(), 1);
    final long dateOffset = DateUtil.getDateOffset(date, -limit);
    int position = 0;
    ArrayList<Budget> res = new ArrayList<>(limit);

    while (date != dateOffset && position < budgets.size()) {
      Budget budget = budgets.get(position);
      if (isAtTheSameDate(date, budget)) {
        budget.setGoal(goal);
        res.add(budget);
        position++;
      } else {
        res.add(new Budget(0, DateUtil.get(date, Calendar.MONTH), DateUtil.get(date, Calendar.YEAR),
            goal));
      }
      date = DateUtil.getDateOffset(date, -1);
    }

    return res;
  }
}
