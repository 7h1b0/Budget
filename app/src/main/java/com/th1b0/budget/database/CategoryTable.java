package com.th1b0.budget.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.DbUtil;
import io.reactivex.Observable;
import java.util.ArrayList;

/**
 * Created by 7h1b0
 */

public final class CategoryTable extends Database {

  public CategoryTable(@NonNull Context context) {
    super(context);
  }

  public Observable<ArrayList<Category>> getAll() {
    return db.createQuery(TABLE_CATEGORY, "SELECT "
        + Category.ID
        + ", "
        + Category.TITLE
        + ", "
        + Category.COLOR
        + ","
        + Category.ICON
        + ", "
        + Category.ID_BUDGET
        + ", "
        + Budget.TITLE
        + " FROM "
        + TABLE_CATEGORY
        + " LEFT JOIN "
        + TABLE_BUDGET
        + " ON "
        + Category.ID_BUDGET
        + " = "
        + Budget.ID
        + " ORDER BY "
        + Category.TITLE).map(super::getCursor).map(cursor -> {
      try {
        ArrayList<Category> categories = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          categories.add(getCategory(cursor));
        }
        return categories;
      } finally {
        cursor.close();
      }
    });
  }

  public long add(@NonNull Category category) {
    return db.insert(TABLE_CATEGORY, getContentValues(category));
  }

  public int delete(@NonNull Category category) {
    return db.delete(TABLE_CATEGORY, Category.ID + " = ?", String.valueOf(category.getId()));
  }

  public int update(@NonNull Category category) {
    return db.update(TABLE_CATEGORY, getContentValues(category), Category.ID + " = ?",
        String.valueOf(category.getId()));
  }

  public int removeIdBudget(long idBudget) {
    ContentValues values = new ContentValues();
    values.put(Category.ID_BUDGET, Budget.NOT_DEFINED);
    return db.update(TABLE_CATEGORY, values, Category.ID_BUDGET + " = ?",
        String.valueOf(idBudget));
  }

  private Category getCategory(@NonNull Cursor cursor) {
    Category category =  new Category(DbUtil.getLong(cursor, Category.ID),
        DbUtil.getLong(cursor, Category.ID_BUDGET), DbUtil.getString(cursor, Category.TITLE),
        DbUtil.getInt(cursor, Category.COLOR), DbUtil.getInt(cursor, Category.ICON));
    category.setTitleBudget(DbUtil.getString(cursor, Budget.TITLE));
    return category;
  }

  public Observable<Boolean> isEmpty() {
    return db.createQuery(TABLE_CATEGORY, "SELECT "
        + Category.ID
        + " FROM "
        + TABLE_CATEGORY)
        .map(super::getCursor).map(cursor -> {
      try {
        return cursor.getCount() == 0;
      } finally {
        cursor.close();
      }
    });
  }
}
