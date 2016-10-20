package com.th1b0.budget.model;

import com.th1b0.budget.util.DateUtil;

/**
 * Created by 7h1b0.
 */

public class PresentationHistory implements SimpleItem {

  private int month;
  private int year;
  private double value;

  public PresentationHistory(int month, int year, double value) {
    this.month = month;
    this.year = year;
    this.value = value;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override public String getTitle() {
    return DateUtil.formatDate(year, month);
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }
}
