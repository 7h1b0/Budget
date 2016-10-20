package com.th1b0.budget.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import com.th1b0.budget.R;

/**
 * Created by 7h1b0
 */

public class Category implements Parcelable {

  public static final String CATEGORY = "category";
  public static final String CATEGORIES = "categories";
  public static final String ID = "c_id";
  public static final String TITLE = "c_title";
  public static final String COLOR = "c_color";
  public static final String ICON = "c_icon";
  public static final String ID_CONTAINER = "c_id_container";

  private long id;
  private long idContainer;
  private String titleContainer;
  private String title;
  @ColorInt private int color;
  @DrawableRes private int icon;

  public Category(Parcel in) {
    id = in.readLong();
    title = in.readString();
    color = in.readInt();
    icon = in.readInt();
    idContainer = in.readLong();
    titleContainer = in.readString();
  }

  public Category(@ColorInt int color) {
    this(-1, -1, null, color, R.mipmap.ic_category);
  }

  public Category(long id, long idContainers, String title, @ColorInt int color, @DrawableRes int icon) {
    this.id = id;
    this.idContainer = idContainers;
    this.title = title;
    this.color = color;
    this.icon = icon;
  }

  public Category(long idContainers, String title, @ColorInt int color, @DrawableRes int icon) {
    this(-1, idContainers, title, color, icon);
  }

  public Category(String title, @ColorInt int color, @DrawableRes int icon) {
    this(-1, -1, title, color, icon);
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

  public long getIdContainer() {
    return idContainer;
  }

  public void setIdContainer(long idContainer) {
    this.idContainer = idContainer;
  }

  public String getTitleContainer() {
    return titleContainer;
  }

  public void setTitleContainer(String titleContainer) {
    this.titleContainer = titleContainer;
  }

  public boolean isContainerIdDefined() {
    return idContainer != Container.NOT_DEFINED;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(id);
    dest.writeString(title);
    dest.writeInt(color);
    dest.writeInt(icon);
    dest.writeLong(idContainer);
    dest.writeString(titleContainer);
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
    if (idContainer != category.idContainer) return false;
    if (color != category.color) return false;
    if (icon != category.icon) return false;
    return title != null ? title.equals(category.title) : category.title == null;
  }

  @Override public int hashCode() {
    int result = (int) (id ^ (id >>> 32));
    result = 31 * result + (int) (idContainer ^ (idContainer >>> 32));
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + color;
    result = 31 * result + icon;
    return result;
  }
}
