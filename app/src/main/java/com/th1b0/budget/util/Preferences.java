package com.th1b0.budget.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

/**
 * Created by 7h1b0.
 */

public class Preferences {

  public static final String START = "pref_start";
  public static final String PREF_BUDGET_VALUE = "pref_budget_value";
  public static final String PREF_ENLARGE_FIRST_CELL = "pref_enlarge_first_cell";
  public static final String PREF_FIRST_LAUNCH = "pref_first_launch";

  public static int getBudgetValue(@NonNull final Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    try {
      return Integer.parseInt(sharedPreferences.getString(PREF_BUDGET_VALUE, "250"));
    } catch (NumberFormatException e) {
      return 250;
    }
  }

  public static boolean enlargeFirstCell(@NonNull final Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getBoolean(PREF_ENLARGE_FIRST_CELL, true);
  }

  public static boolean firstLaunch(@NonNull final Context context) {
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
