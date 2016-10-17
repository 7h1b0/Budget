package com.th1b0.budget.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.util.DbUtil;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by 7h1b0.
 */

public final class ContainerTable extends Database {

  public ContainerTable(@NonNull Context context) {
    super(context);
  }

  public Observable<ArrayList<Container>> getAll() {
    return db.createQuery(TABLE_CONTAINER, "SELECT "
        + Container.ID
        + ", "
        + Container.TITLE
        + ", "
        + Container.VALUE
        + " FROM "
        + TABLE_CONTAINER
        + " ORDER BY "
        + Container.TITLE).map(super::getCursor).map(cursor -> {
      try {
        ArrayList<Container> containers = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          containers.add(getContainer(cursor));
        }
        return containers;
      } finally {
        cursor.close();
      }
    });
  }

  public long add(@NonNull Container container) {
    return db.insert(TABLE_CONTAINER, getContentValues(container));
  }

  public int delete(@NonNull Container container) {
    return db.delete(TABLE_CONTAINER, Container.ID + " = ?", String.valueOf(container.getId()));
  }

  public int update(@NonNull Container container) {
    return db.update(TABLE_CONTAINER, getContentValues(container), Container.ID + " = ?",
        String.valueOf(container.getId()));
  }

  private ContentValues getContentValues(@NonNull Container container) {
    ContentValues values = new ContentValues();
    values.put(Container.TITLE, container.getTitle());
    values.put(Container.VALUE, container.getValue());
    return values;
  }

  private Container getContainer(@NonNull Cursor cursor) {
    return new Container(DbUtil.getLong(cursor, Container.ID),
        DbUtil.getString(cursor, Container.TITLE), DbUtil.getDouble(cursor, Container.VALUE));
  }
}
