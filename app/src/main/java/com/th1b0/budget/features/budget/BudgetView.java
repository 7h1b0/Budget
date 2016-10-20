package com.th1b0.budget.features.budget;

import android.content.Context;
import com.th1b0.budget.model.PresentationBalance;
import com.th1b0.budget.model.PresentationBudget;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface BudgetView {
  void onBudgetLoaded(ArrayList<PresentationBudget> budgets);

  void onBalanceLoaded(PresentationBalance balance);

  void onError(String error);

  Context getContext();
}
