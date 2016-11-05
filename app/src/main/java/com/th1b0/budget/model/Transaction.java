package com.th1b0.budget.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import com.th1b0.budget.util.DateUtil;

/**
 * Created by 7h1b0.
 */

public class Transaction implements Parcelable, TransactionItem {

  public static final String TRANSACTION = "transaction";
  public static final String ID = "t_id";
  public static final String DAY = "t_day";
  public static final String MONTH = "t_month";
  public static final String YEAR = "t_year";
  public static final String VALUE = "t_value";
  public static final String ID_CATEGORY = "t_category";
  public static final String DESCRIPTION = "t_description";
  public static final String ID_BUDGET = "t_budget";

  private long id;
  private int day;
  private int month;
  private int year;
  private double value;
  private long idCategory;
  private int color;
  private int icon;
  private String description;
  private long idBudget;

  public Transaction() {
    this(-1, DateUtil.getCurrentDay(), DateUtil.getCurrentMonth(), DateUtil.getCurrentYear(), 0, -1, null, -1);
  }

  public Transaction(Parcel in) {
    this.id = in.readLong();
    this.day = in.readInt();
    this.month = in.readInt();
    this.year = in.readInt();
    this.value = in.readDouble();
    this.idCategory = in.readLong();
    this.description = in.readString();
    this.color = in.readInt();
    this.icon = in.readInt();
    this.idBudget = in.readLong();
  }

  public Transaction(int day, int month, int year, double value, int idCategory, String description, long idBudget) {
    this(-1, day, month, year, value, idCategory, description, idBudget);
  }

  public Transaction(long id, int day, int month, int year, double value, long idCategory, String description, long idBudget) {
    this(id, day, month, year, value, idCategory, description, -1, -1, idBudget);
  }

  public Transaction(long id, int day, int month, int year, double value, long idCategory, String description, @ColorInt int color, @DimenRes int icon, long idBudget) {
    this.id = id;
    this.day = day;
    this.month = month;
    this.year = year;
    this.value = value;
    this.idCategory = idCategory;
    this.description = description;
    this.color = color;
    this.icon = icon;
    this.idBudget = idBudget;
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

  public long getIdCategory() {
    return idCategory;
  }

  public void setIdCategory(long idCategory) {
    this.idCategory = idCategory;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getColor() {
    return color;
  }

  public int getIcon() {
    return icon;
  }

  public long getIdBudget() {
    return idBudget;
  }

  public void setIdBudget(long idBudget) {
    this.idBudget = idBudget;
  }

  public boolean isBudgetIdDefined() {
    return idBudget != Budget.NOT_DEFINED;
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
    dest.writeLong(idCategory);
    dest.writeString(description);
    dest.writeInt(color);
    dest.writeInt(icon);
    dest.writeLong(idBudget);
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
    result = 31 * result + (int) (idCategory ^ (idCategory >>> 32));
    result = 31 * result + color;
    result = 31 * result + icon;
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }
}
