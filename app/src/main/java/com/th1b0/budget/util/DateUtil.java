package com.th1b0.budget.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by 7h1b0.
 */

public final class DateUtil {

  public static String getMonthName(int month) {
    long timestamp = getTimestamp(2016, month, 1);
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.getDefault());
    return sdf.format(new Date(timestamp));
  }

  public static long getTimestamp(int year, int month, int day) {
    GregorianCalendar c = new GregorianCalendar(year, month, day);
    return c.getTimeInMillis();
  }

  public static String formatDate(long timestamp) {
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d", Locale.getDefault());
    return sdf.format(new Date(timestamp));
  }

  public static String formatDate(int year, int month, int day) {
    return formatDate(getTimestamp(year, month, day));
  }

  public static String formatDate(int year, int month) {
    long timestamp = getTimestamp(year, month, 1);
    SimpleDateFormat sdf = new SimpleDateFormat("MMMM YYYY", Locale.getDefault());
    return sdf.format(new Date(timestamp));
  }

  public static int get(long timestamp, int field) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(timestamp);
    return calendar.get(field);
  }

  public static int getCurrentMonth() {
    long now = new Date().getTime();
    return get(now, Calendar.MONTH);
  }

  public static int getCurrentYear() {
    long now = new Date().getTime();
    return get(now, Calendar.YEAR);
  }

  public static int getCurrentDay() {
    long now = new Date().getTime();
    return get(now, Calendar.DAY_OF_MONTH);
  }
}

