package com.th1b0.budget.database;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.Transaction;
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

  public Observable<ArrayList<Budget>> getAll(int limit) {
    return db.createQuery(TABLE_TRANSACTION, "SELECT "
        + Transaction.MONTH
        + ", "
        + Transaction.YEAR
        + ", "
        + "SUM("
        + Transaction.VALUE
        + ") as "
        + Budget.VALUE
        + " FROM "
        + TABLE_TRANSACTION
        + " AS t JOIN "
        + TABLE_CATEGORY
        + " AS c ON "
        + Transaction.ID_CATEGORY
        + " = "
        + Category.ID
        + " WHERE "
        + Category.INCLUDE_IN_BUDGET
        + " = 1 "
        + " GROUP BY "
        + Transaction.MONTH
        + ", "
        + Transaction.YEAR
        + " ORDER BY "
        + Transaction.YEAR
        + " DESC, "
        + Transaction.MONTH
        + " DESC LIMIT "
        + limit).map(super::getCursor).map(cursor -> {
      try {
        ArrayList<Budget> budgets = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          Budget budget = new Budget(DbUtil.getDouble(cursor, Budget.VALUE),
              DbUtil.getInt(cursor, Transaction.MONTH), DbUtil.getInt(cursor, Transaction.YEAR));
          budgets.add(budget);
        }
        return budgets;
      } finally {
        cursor.close();
      }
    });
  }
}
