package com.th1b0.budget.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 7h1b0.
 * DEBUG Only
 */

public final class Logger {

  public static void e(@NonNull String tag, @Nullable String message) {
    if (tag.length() > 21) {
      tag = tag.substring(0, 21);
    }
    message = Thread.currentThread().getName() + ": " + message;
    Log.e(tag, message);
  }
}
