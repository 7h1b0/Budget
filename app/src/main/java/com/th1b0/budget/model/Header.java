package com.th1b0.budget.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 7h1b0.
 */

public class Header implements Parcelable, TransactionItem {

  private String title;

  public Header(String title) {
    this.title = title;
  }

  public Header(Parcel in) {
    title = in.readString();
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int i) {
    dest.writeString(title);
  }

  @Override public String getTitle() {
    return title;
  }

  @Override @ViewType public int getType() {
    return TYPE_HEADER;
  }

  public static final Parcelable.Creator<Header> CREATOR = new Parcelable.Creator<Header>() {
    @Override public Header createFromParcel(Parcel source) {
      return new Header(source);
    }

    @Override public Header[] newArray(int size) {
      return new Header[size];
    }
  };

  @Override public int hashCode() {
    return title != null ? title.hashCode() : 0;
  }
}
