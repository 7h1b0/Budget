package com.th1b0.budget.features.budgetmonth;

import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0
 */

interface BudgetMonthPresenter  extends Presenter {
  void loadBudgets(int year, int month);
}
