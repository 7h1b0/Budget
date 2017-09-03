package com.th1b0.budget.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import java.lang.ref.WeakReference;

/**
 * Created by 7h1b0.
 */

public abstract class PresenterImpl<T> {

  private WeakReference<T> mView;
  protected final DataManager mDataManager;
  protected final CompositeDisposable mSubscription;

  public PresenterImpl(@NonNull final DataManager dataManager) {
    mDataManager = dataManager;
    mSubscription = new CompositeDisposable();
  }

  @Nullable protected T getView() {
    return mView.get();
  }

  public void attach(@NonNull final T view) {
    mView = new WeakReference<>(view);
  }

  public void detach() {
    mSubscription.clear();
    mView.clear();
  }
}

