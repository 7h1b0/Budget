package com.th1b0.budget.util;

import android.database.Cursor;

/**
 * Created by 7h1b0.
 */

public final class DbUtil {

  public static String getString(Cursor cursor, String columnName) {
    return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
  }

  public static int getInt(Cursor cursor, String columnName) {
    return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
  }

  public static long getLong(Cursor cursor, String columnName) {
    return cursor.getLong(cursor.getColumnIndexOrThrow(columnName));
  }

  public static double getDouble(Cursor cursor, String columnName) {
    return cursor.getDouble(cursor.getColumnIndexOrThrow(columnName));
  }
}
