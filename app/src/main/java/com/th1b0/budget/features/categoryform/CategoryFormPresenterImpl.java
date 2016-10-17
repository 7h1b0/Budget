package com.th1b0.budget.features.categoryform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;

/**
 * Created by 7h1b0.
 */

public class CategoryFormPresenterImpl extends PresenterImpl<Object>
    implements CategoryFormPresenter {
  public CategoryFormPresenterImpl(@NonNull Object view, @NonNull DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void addCategory(@NonNull Category category) {
    mDataManager.addCategory(category);
  }

  @Override public void editCategory(@NonNull Category category) {
    mDataManager.updateCategory(category);
  }
}
