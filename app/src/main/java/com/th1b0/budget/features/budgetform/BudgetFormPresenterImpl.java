package com.th1b0.budget.features.budgetform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;

/**
 * Created by 7h1b0.
 */

final class BudgetFormPresenterImpl extends PresenterImpl<Object> implements BudgetFormPresenter {

  BudgetFormPresenterImpl(@NonNull Object view, @NonNull DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void addBudget(@NonNull Budget budget) {
    mDataManager.addBudget(budget);
  }

  @Override public void updateBudget(@NonNull Budget budget) {
    mDataManager.updateBudget(budget);
  }
}
