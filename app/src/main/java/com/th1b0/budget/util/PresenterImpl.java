package com.th1b0.budget.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 7h1b0.
 */

public abstract class PresenterImpl<T> {

  private final WeakReference<T> mView;
  protected final DataManager mDataManager;
  protected final CompositeSubscription mSubscription;

  public PresenterImpl(@NonNull final T view, @NonNull final DataManager dataManager) {
    mView = new WeakReference<>(view);
    mDataManager = dataManager;
    mSubscription = new CompositeSubscription();
  }

  @Nullable protected T getView() {
    return mView.get();
  }

  public void detach() {
    mSubscription.clear();
    mView.clear();
  }
}

