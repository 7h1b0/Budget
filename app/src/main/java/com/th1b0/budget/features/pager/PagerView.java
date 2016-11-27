package com.th1b0.budget.features.pager;

import android.content.Context;
import com.th1b0.budget.model.PresentationBalance;

/**
 * Created by 7h1b0.
 */

interface PagerView {
  //void onBudgetLoaded(ArrayList<PresentationBudget> budgets);

  void onBalanceLoaded(PresentationBalance balance);

  void onError(String error);

  Context getContext();
}
