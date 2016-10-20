package com.th1b0.budget.features.containerform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;

/**
 * Created by 7h1b0.
 */

final class ContainerFormPresenterImpl extends PresenterImpl<Object> implements ContainerFormPresenter {

  ContainerFormPresenterImpl(@NonNull Object view, @NonNull DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void addContainer(@NonNull Container container) {
    mDataManager.addContainers(container);
  }

  @Override public void updateContainer(@NonNull Container container) {
    mDataManager.updateContainer(container);
  }
}
