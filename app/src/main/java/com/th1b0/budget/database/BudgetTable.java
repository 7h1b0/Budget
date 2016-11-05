package com.th1b0.budget.database;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DbUtil;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by 7h1b0.
 */

public final class BudgetTable extends Database {

  public BudgetTable(@NonNull Context context) {
    super(context);
  }

  public Observable<ArrayList<Budget>> getAll() {
    return db.createQuery(TABLE_BUDGET, "SELECT "
        + Budget.ID
        + ", "
        + Budget.TITLE
        + ", "
        + Budget.VALUE
        + " FROM "
        + TABLE_BUDGET
        + " ORDER BY "
        + Budget.TITLE).map(super::getCursor).map(cursor -> {
      try {
        ArrayList<Budget> budgets = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          budgets.add(getBudget(cursor));
        }
        return budgets;
      } finally {
        cursor.close();
      }
    });
  }

  public long add(@NonNull Budget budget) {
    return db.insert(TABLE_BUDGET, getContentValues(budget));
  }

  public int delete(@NonNull Budget budget) {
    return db.delete(TABLE_BUDGET, Budget.ID + " = ?", String.valueOf(budget.getId()));
  }

  public int update(@NonNull Budget budget) {
    return db.update(TABLE_BUDGET, getContentValues(budget), Budget.ID + " = ?",
        String.valueOf(budget.getId()));
  }

  private Budget getBudget(@NonNull Cursor cursor) {
    return new Budget(DbUtil.getLong(cursor, Budget.ID),
        DbUtil.getString(cursor, Budget.TITLE), DbUtil.getDouble(cursor, Budget.VALUE));
  }
}
