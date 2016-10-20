package com.th1b0.budget.features.transactionform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface TransactionFormPresenter extends Presenter {

  void addTransaction(@NonNull Transaction transaction);

  void updateTransaction(@NonNull Transaction transaction);

  void loadCategoriesAndContainers();
}
