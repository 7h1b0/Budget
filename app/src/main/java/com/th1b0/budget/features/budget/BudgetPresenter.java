package com.th1b0.budget.features.budget;

import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface BudgetPresenter extends Presenter {
  void loadBudgets(int month, int year);

  void loadBalance(int month, int year);
}
