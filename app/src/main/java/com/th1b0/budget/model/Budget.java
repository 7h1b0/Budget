package com.th1b0.budget.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 7h1b0.
 */

public class Budget implements Parcelable {

  public static final String BUDGET = "budget";
  public static final String VALUE = "value";

  private double value;
  private double goal;
  private int month;
  private int year;
  private String date;

  public Budget(Parcel in) {
    value = in.readDouble();
    month = in.readInt();
    year = in.readInt();
    goal = in.readDouble();
    date = in.readString();
  }

  public Budget(double value, int month, int year) {
    this(value, month, year, 0);
  }

  public Budget(double value, int month, int year, double goal) {
    this.value = value;
    this.month = month;
    this.year = year;
    this.goal = goal;
  }

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
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

  public double getGoal() {
    return goal;
  }

  public void setGoal(double goal) {
    this.goal = goal;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeDouble(value);
    dest.writeInt(month);
    dest.writeInt(year);
    dest.writeDouble(goal);
    dest.writeString(date);
  }

  public static final Parcelable.Creator<Budget> CREATOR = new Parcelable.Creator<Budget>() {
    @Override public Budget createFromParcel(Parcel source) {
      return new Budget(source);
    }

    @Override public Budget[] newArray(int size) {
      return new Budget[size];
    }
  };

  @Override public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(value);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(goal);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + month;
    result = 31 * result + year;
    result = 31 * result + (date != null ? date.hashCode() : 0);
    return result;
  }
}
