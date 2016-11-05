package com.th1b0.budget.features.budget;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface BudgetPresenter extends Presenter {

  void loadBudgets();

  void deleteBudget(@NonNull Budget budget);
}
