package com.th1b0.budget.util;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import com.th1b0.budget.R;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by 7h1b0.
 */

public final class CategorieUtil {

  @Retention(SOURCE)
  @IntDef({ CATEGORY_FOOD, CATEGORY_DINER, CATEGORY_HOBBY, CATEGORY_SHOPPING, CATEGORY_TRANSPORT, CATEGORY_SCHOOL_SUPPLIES })
  public @interface Category {
  }

  public static final int CATEGORY_FOOD = 0;
  public static final int CATEGORY_DINER = 1;
  public static final int CATEGORY_HOBBY = 2;
  public static final int CATEGORY_SHOPPING = 3;
  public static final int CATEGORY_TRANSPORT = 4;
  public static final int CATEGORY_SCHOOL_SUPPLIES = 5;

  @DrawableRes public static int getIcon(@Category int category) {
    switch (category) {
      case CATEGORY_DINER:
        return R.mipmap.ic_diner;
      case CATEGORY_FOOD:
      default:
        return R.mipmap.ic_food;
      case CATEGORY_HOBBY:
        return R.mipmap.ic_hobby;
      case CATEGORY_SHOPPING:
        return R.mipmap.ic_shopping;
      case CATEGORY_TRANSPORT:
        return R.mipmap.ic_transport;
      case CATEGORY_SCHOOL_SUPPLIES:
        return R.mipmap.ic_school;
    }
  }

  @ColorRes public static int getColor(@Category int category) {
    switch (category) {
      case CATEGORY_DINER:
        return R.color.category_diner;
      case CATEGORY_FOOD:
      default:
        return R.color.category_food;
      case CATEGORY_HOBBY:
        return R.color.category_hobby;
      case CATEGORY_SHOPPING:
        return R.color.category_shopping;
      case CATEGORY_TRANSPORT:
        return R.color.category_transport;
      case CATEGORY_SCHOOL_SUPPLIES:
        return R.color.category_school_supplies;
    }
  }
}
