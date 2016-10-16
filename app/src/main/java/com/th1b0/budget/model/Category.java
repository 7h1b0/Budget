package com.th1b0.budget.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;

/**
 * Created by 7h1b0
 */

public class Category implements Parcelable {

  public static final String CATEGORY = "category";
  public static final String ID = "c_id";
  public static final String TITLE = "c_title";
  public static final String COLOR = "c_color";
  public static final String ICON = "c_icon";
  public static final String INCLUDE_IN_BUDGET = "c_include_on_budget";

  private long id;
  private String title;
  @ColorInt private int color;
  @DrawableRes private int icon;
  private boolean includeInBudget;

  public Category(Parcel in) {
    id = in.readLong();
    title = in.readString();
    color = in.readInt();
    icon = in.readInt();
    includeInBudget = in.readByte() == 1;
  }

  public Category(long id, String title, @ColorInt int color, @DrawableRes int icon, boolean includeInBudget) {
    this.id = id;
    this.title = title;
    this.color = color;
    this.icon = icon;
    this.includeInBudget = includeInBudget;
  }

  public Category(String title, @ColorInt int color, @DrawableRes int icon, boolean includeInBudget) {
    this(-1, title, color, icon, includeInBudget);
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

  @ColorInt public int getColor() {
    return color;
  }

  public void setColor(@ColorInt int color) {
    this.color = color;
  }

  @DrawableRes public int getIcon() {
    return icon;
  }

  public void setIcon(@DrawableRes int icon) {
    this.icon = icon;
  }

  public boolean isIncludeInBudget() {
    return includeInBudget;
  }

  public void setIncludeInBudget(boolean includeInBudget) {
    this.includeInBudget = includeInBudget;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeString(title);
    dest.writeInt(color);
    dest.writeInt(icon);
    dest.writeByte(includeInBudget ? (byte) 1 : (byte) 0);
  }

  public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
    @Override public Category createFromParcel(Parcel source) {
      return new Category(source);
    }

    @Override public Category[] newArray(int size) {
      return new Category[size];
    }
  };

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Category category = (Category) o;

    if (id != category.id) return false;
    if (color != category.color) return false;
    if (icon != category.icon) return false;
    if (includeInBudget != category.includeInBudget) return false;
    return title != null ? title.equals(category.title) : category.title == null;
  }

  @Override public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + color;
    result = 31 * result + icon;
    result = 31 * result + (includeInBudget ? 1 : 0);
    return result;
  }
}
