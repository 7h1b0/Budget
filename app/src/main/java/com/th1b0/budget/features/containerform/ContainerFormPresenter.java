package com.th1b0.budget.features.containerform;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface ContainerFormPresenter extends Presenter {

  void addContainer(@NonNull Container container);

  void updateContainer(@NonNull Container container);
}
