package com.th1b0.budget.util;

import android.support.annotation.NonNull;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 7h1b0.
 */

public abstract class PresenterImpl<T> {

  protected final T mView;
  protected final DataManager mDataManager;
  protected final CompositeSubscription mSubscription;

  public PresenterImpl(@NonNull final T view, @NonNull final DataManager dataManager) {
    mView = view;
    mDataManager = dataManager;
    mSubscription = new CompositeSubscription();
  }

  public void detach() {
    mSubscription.clear();
  }
}

