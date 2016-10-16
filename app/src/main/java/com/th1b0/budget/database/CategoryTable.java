package com.th1b0.budget.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.squareup.sqlbrite.BriteDatabase;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.DbUtil;
import java.util.ArrayList;
import rx.Observable;

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
        + Category.INCLUDE_IN_BUDGET
        + " FROM "
        + TABLE_TRANSACTION
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

  public void add(ArrayList<Category> categories) {
    BriteDatabase.Transaction transaction = db.newTransaction();
    try {
      for (Category category : categories) {
        db.insert(TABLE_CATEGORY, getContentValues(category));
      }
      transaction.markSuccessful();
    } finally {
      transaction.end();
    }
  }

  public int delete(@NonNull Category category) {
    return db.delete(TABLE_CATEGORY, Category.ID + " = ?", String.valueOf(category.getId()));
  }

  public int update(@NonNull Category category) {
    return db.update(TABLE_CATEGORY, getContentValues(category), Category.ID + " = ?",
        String.valueOf(category.getId()));
  }

  private ContentValues getContentValues(@NonNull Category category) {
    ContentValues values = new ContentValues();
    values.put(Category.TITLE, category.getTitle());
    values.put(Category.COLOR, category.getColor());
    values.put(Category.ICON, category.getIcon());
    values.put(Category.INCLUDE_IN_BUDGET, category.isIncludeInBudget());

    return values;
  }

  private Category getCategory(@NonNull Cursor cursor) {
    return new Category(DbUtil.getLong(cursor, Category.ID),
        DbUtil.getString(cursor, Category.TITLE), DbUtil.getInt(cursor, Category.COLOR),
        DbUtil.getInt(cursor, Category.ICON),
        DbUtil.getBoolean(cursor, Category.INCLUDE_IN_BUDGET));
  }
}
