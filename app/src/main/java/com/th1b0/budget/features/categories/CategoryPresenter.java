package com.th1b0.budget.features.categories;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0
 */

interface CategoryPresenter extends Presenter {
  void loadCategory();

  void deleteCategory(@NonNull Category category);
}
