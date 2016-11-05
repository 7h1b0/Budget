package com.th1b0.budget.features.categoryform;

import android.content.Context;
import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface CategoryFormView {

  void onBudgetLoaded(ArrayList<Budget> budgets);

  void onError(String error);

  @NonNull Context getContext();
}
