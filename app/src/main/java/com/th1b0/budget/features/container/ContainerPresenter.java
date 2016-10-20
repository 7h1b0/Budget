package com.th1b0.budget.features.container;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface ContainerPresenter extends Presenter {

  void loadContainers();

  void deleteContainer(@NonNull Container container);
}
