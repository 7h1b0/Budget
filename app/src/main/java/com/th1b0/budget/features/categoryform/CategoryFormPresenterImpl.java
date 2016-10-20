package com.th1b0.budget.features.categoryform;

import android.support.annotation.NonNull;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class CategoryFormPresenterImpl extends PresenterImpl<CategoryFormView>
    implements CategoryFormPresenter {

  CategoryFormPresenterImpl(@NonNull CategoryFormView view, @NonNull DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void addCategory(@NonNull Category category) {
    mDataManager.addCategory(category);
  }

  @Override public void updateCategory(@NonNull Category category) {
    mDataManager.updateCategory(category);
  }

  @Override public void loadContainer() {
    mSubscription.add(mDataManager.getContainers()
        .map(containers -> {
          containers.add(0,
              new Container(Container.NONE, getView().getContext().getString(R.string.none), 0));
          return containers;
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(containers -> {
          if (isViewAttached()) {
            getView().onContainerLoaded(containers);
          }
        }, error -> {
          if (isViewAttached()) {
            getView().onError(error.getMessage());
          }
        }));
  }
}
