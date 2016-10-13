package com.th1b0.budget.model;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by 7h1b0.
 */

public interface RecyclerItem {
  @Retention(SOURCE)
  @IntDef({ TYPE_HEADER, TYPE_TRANSACTION }) @interface ViewType {}

  int TYPE_HEADER = 0;
  int TYPE_TRANSACTION = 1;

  String getTitle();

  @ViewType int getType();
}
