package com.th1b0.budget.features.transactionform;

import android.content.Context;
import android.support.annotation.NonNull;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.Container;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface TransactionFormView {
  void onCategoriesLoaded(ArrayList<Category> categories);

  void onContainersLoaded(ArrayList<Container> containers);

  void onError(String error);

  @NonNull Context getContext();
}
