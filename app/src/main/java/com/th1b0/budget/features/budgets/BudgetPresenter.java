package com.th1b0.budget.features.budgets;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.BasePresenter;

/**
 * Created by 7h1b0.
 */

interface BudgetPresenter extends BasePresenter<BudgetView> {

  void loadBudgets();

  void deleteBudget(@NonNull Budget budget);
}
