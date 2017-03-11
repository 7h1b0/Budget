package com.th1b0.budget.util;

/**
 * Created by 7h1b0.
 */

public interface BasePresenter<T> {
  void attach(T view);

  void detach();
}
