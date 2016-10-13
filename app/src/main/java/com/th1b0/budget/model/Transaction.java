package com.th1b0.budget.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.th1b0.budget.util.CategorieUtil;
import com.th1b0.budget.util.DateUtil;

/**
 * Created by 7h1b0.
 */

public class Transaction implements Parcelable, RecyclerItem {

  public static final String TRANSACTION = "transaction";
  public static final String ID = "id";
  public static final String DAY = "day";
  public static final String MONTH = "month";
  public static final String YEAR = "year";
  public static final String VALUE = "value";
  public static final String CATEGORY = "category";
  public static final String DESCRIPTION = "description";

  private long id;
  private int day;
  private int month;
  private int year;
  private double value;
  private int category;
  private String description;

  public Transaction() {
    this(-1, DateUtil.getCurrentDay(), DateUtil.getCurrentMonth(), DateUtil.getCurrentYear(), 0, CategorieUtil.CATEGORY_FOOD, null);
  }

  public Transaction(Parcel in) {
    this.id = in.readLong();
    this.day = in.readInt();
    this.month = in.readInt();
    this.year = in.readInt();
    this.value = in.readDouble();
    this.category = in.readInt();
    this.description = in.readString();
  }

  public Transaction(int day, int month, int year, double value, @CategorieUtil.Category int category, String description) {
    this(-1, day, month, year, value, category, description);
  }

  public Transaction(long id, int day, int month, int year, double value, @CategorieUtil.Category int category, String description) {
    this.id = id;
    this.day = day;
    this.month = month;
    this.year = year;
    this.value = value;
    this.category = category;
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
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

  public double getValue() {
    return value;
  }

  public void setValue(double value) {
    this.value = value;
  }

  @CategorieUtil.Category public int getCategory() {
    return category;
  }

  public void setCategory(@CategorieUtil.Category int category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int i) {
    dest.writeLong(id);
    dest.writeInt(day);
    dest.writeInt(month);
    dest.writeInt(year);
    dest.writeDouble(value);
    dest.writeInt(category);
    dest.writeString(description);
  }

  public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
    @Override public Transaction createFromParcel(Parcel source) {
      return new Transaction(source);
    }

    @Override public Transaction[] newArray(int size) {
      return new Transaction[size];
    }
  };

  @Override public String getTitle() {
    return this.description;
  }

  @Override @ViewType public int getType() {
    return TYPE_TRANSACTION;
  }

  @Override public int hashCode() {
    int result;
    long temp;
    result = (int) (id ^ (id >>> 32));
    result = 31 * result + day;
    result = 31 * result + month;
    result = 31 * result + year;
    temp = Double.doubleToLongBits(value);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + category;
    result = 31 * result + description.hashCode();
    return result;
  }
}
