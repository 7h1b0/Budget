package com.th1b0.budget.features.transactionform;

import com.th1b0.budget.model.Category;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public interface TransactionFormView {
  void onCategoriesLoaded(ArrayList<Category> categories);

  void onError(String error);
}
