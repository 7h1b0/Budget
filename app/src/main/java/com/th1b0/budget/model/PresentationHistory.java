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

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PresentationHistory that = (PresentationHistory) o;

    if (month != that.month) return false;
    if (year != that.year) return false;
    return Double.compare(that.value, value) == 0;
  }

  @Override public int hashCode() {
    int result;
    long temp;
    result = month;
    result = 31 * result + year;
    temp = Double.doubleToLongBits(value);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
