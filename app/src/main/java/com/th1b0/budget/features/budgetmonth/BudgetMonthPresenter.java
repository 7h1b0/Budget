package com.th1b0.budget.features.budgetmonth;

import com.th1b0.budget.util.BasePresenter;

/**
 * Created by 7h1b0
 */

interface BudgetMonthPresenter extends BasePresenter<BudgetMonthView> {
  void loadBudgets(int year, int month);

  void loadBalance(int month, int year);
}
