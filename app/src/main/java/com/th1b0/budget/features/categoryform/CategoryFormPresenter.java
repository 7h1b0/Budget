package com.th1b0.budget.features.categoryform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

public interface CategoryFormPresenter extends Presenter {

  void addCategory(@NonNull Category category);

  void editCategory(@NonNull Category category);
}
