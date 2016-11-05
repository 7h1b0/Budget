package com.th1b0.budget.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 7h1b0.
 */

public class Budget implements Parcelable, SimpleItem{

  public static final String BUDGETS = "budgets";
  public static final String BUDGET = "budget";
  public static final String ID = "b_id";
  public static final String TITLE = "b_title";
  public static final String VALUE = "b_value";

  public static final long NONE = -2;
  public static final long NOT_DEFINED = -1;

  private long id;
  private String title;
  private double value;

  public Budget() {

  }

  public Budget(String title, double value) {
    this(-1, title, value);
  }

  public Budget(long id, String title, double value) {
    this.id = id;
    this.title = title;
    this.value = value;
  }

  public Budget(Parcel in) {
    this.id = in.readLong();
    this.title = in.readString();
    this.value = in.readDouble();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Override public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeString(title);
    dest.writeDouble(value);
  }

  public static final Parcelable.Creator<Budget> CREATOR = new Parcelable.Creator<Budget>() {
    @Override public Budget createFromParcel(Parcel source) {
      return new Budget(source);
    }

    @Override public Budget[] newArray(int size) {
      return new Budget[size];
    }
  };

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Budget that = (Budget) o;

    return id == that.id;
  }

  @Override public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }
}
