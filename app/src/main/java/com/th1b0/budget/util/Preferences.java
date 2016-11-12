package com.th1b0.budget.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Created by 7h1b0.
 */

public class Preferences {
  private static final String PREF_FIRST_LAUNCH = "pref_first_launch";

  public static boolean isFirstLaunch(@NonNull final Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getBoolean(PREF_FIRST_LAUNCH, true);
  }

  public static void setFirstLaunch(@NonNull final Context context, boolean firstLaunch) {
    PreferenceManager.getDefaultSharedPreferences(context)
        .edit()
        .putBoolean(PREF_FIRST_LAUNCH, firstLaunch)
        .apply();
  }
}
