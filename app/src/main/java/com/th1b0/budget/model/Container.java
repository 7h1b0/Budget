package com.th1b0.budget.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 7h1b0.
 */

public class Container implements Parcelable{

  public static final String CONTAINERS = "containers";
  public static final String ID = "co_id";
  public static final String TITLE = "co_title";
  public static final String VALUE = "co_value";

  private long id;
  private String title;
  private double value;

  public Container() {

  }

  public Container(String title, double value) {
    this(-1, title, value);
  }

  public Container(long id, String title, double value) {
    this.id = id;
    this.title = title;
    this.value = value;
  }

  public Container(Parcel in) {
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getValue() {
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

  public static final Parcelable.Creator<Container> CREATOR = new Parcelable.Creator<Container>() {
    @Override public Container createFromParcel(Parcel source) {
      return new Container(source);
    }

    @Override public Container[] newArray(int size) {
      return new Container[size];
    }
  };

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Container that = (Container) o;

    return id == that.id;
  }

  @Override public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }
}
