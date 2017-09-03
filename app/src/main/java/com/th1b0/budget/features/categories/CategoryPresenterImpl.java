package com.th1b0.budget.features.categories;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 7h1b0
 */

final class CategoryPresenterImpl extends PresenterImpl<CategoryView> implements CategoryPresenter {

  CategoryPresenterImpl(@NonNull DataManager dataManager) {
    super(dataManager);
  }

  @Override public void loadCategory() {
    mSubscription.add(mDataManager.getCategories()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(categories -> {
          CategoryView view = getView();
          if (view != null) {
            view.onCategoryLoaded(categories);
          }
        }, error -> {
          CategoryView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }

  @Override public void deleteCategory(@NonNull Category category) {
    mSubscription.add(mDataManager.deleteCategory(category)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ignored -> {
        }, error -> {
          CategoryView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }
}
