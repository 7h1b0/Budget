package com.th1b0.budget.features.budgets;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.th1b0.budget.model.Budget;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface BudgetView {

  void onBudgetsLoaded(@NonNull ArrayList<Budget> budgets);

  void onError(@Nullable String error);
}
