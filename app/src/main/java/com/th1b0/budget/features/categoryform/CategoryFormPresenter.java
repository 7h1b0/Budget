package com.th1b0.budget.features.categoryform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface CategoryFormPresenter extends Presenter {

  void addCategory(@NonNull Category category);

  void updateCategory(@NonNull Category category);

  void loadContainer();
}
