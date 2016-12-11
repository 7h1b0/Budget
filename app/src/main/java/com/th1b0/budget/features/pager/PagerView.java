package com.th1b0.budget.features.pager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.th1b0.budget.model.PresentationBalance;

/**
 * Created by 7h1b0.
 */

interface PagerView {
  void onBalanceLoaded(@NonNull PresentationBalance balance);

  void onError(@Nullable String error);

  Context getContext();
}
