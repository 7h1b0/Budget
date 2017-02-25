package com.th1b0.budget.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 7h1b0.
 * DEBUG Only
 */

public final class Logger {

  public static void e(@NonNull String tag, @NonNull String... messages) {
    if (tag.length() > 21) {
      tag = tag.substring(0, 21);
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < messages.length; i++) {
      sb.append(messages[i]);
    }

    String message = Thread.currentThread().getName() + ": " + sb.toString();
    Log.e(tag, message);
  }
}
