package com.th1b0.budget.features.budgetform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface BudgetFormPresenter extends Presenter {

  void addBudget(@NonNull Budget budget);

  void updateBudget(@NonNull Budget budget);
}
