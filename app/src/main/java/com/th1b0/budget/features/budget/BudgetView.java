package com.th1b0.budget.features.budget;

import com.th1b0.budget.model.Budget;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface BudgetView {

  void onBudgetsLoaded(ArrayList<Budget> budgets);

  void onError(String error);
}
