package com.th1b0.budget.features.budgetform;

import android.support.annotation.Nullable;

/**
 * Created by 7h1b0
 */

interface BudgetFormView {

  void onAddSucceeded();

  void onUpdateSucceeded();

  void onError(@Nullable String error);
}
