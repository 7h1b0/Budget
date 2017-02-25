package com.th1b0.budget.features.transactionform;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Category;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface TransactionFormView {
  void onCategoriesLoaded(@NonNull ArrayList<Category> categories);

  void onBudgetsLoaded(@NonNull ArrayList<Budget> budgets);

  void onError(@Nullable String error);

  void onAddSucceeded();

  void onUpdateSucceeded();

  @NonNull Context getContext();
}
