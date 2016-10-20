package com.th1b0.budget.database;

import android.content.Context;
import android.support.annotation.NonNull;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.model.PresentationBudget;
import com.th1b0.budget.model.PresentationHistory;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DbUtil;
import java.util.ArrayList;
import rx.Observable;

import static com.th1b0.budget.model.PresentationBudget.OUT;

/**
 * Created by 7h1b0.
 */

public final class BudgetTable extends Database {

  public BudgetTable(@NonNull Context context) {
    super(context);
  }

  public Observable<ArrayList<PresentationBudget>> getAll(int month, int year) {
    return db.createQuery(TABLE_TRANSACTION, "SELECT SUM("
        + Transaction.VALUE
        + ") AS "
        + OUT
        + ", "
        + Container.TITLE
        + ", "
        + Container.VALUE
        + ", "
        + Container.ID
        + " FROM "
        + TABLE_CONTAINER
        + " JOIN "
        + TABLE_TRANSACTION
        + " ON "
        + Container.ID
        + " = "
        + Transaction.ID_CONTAINER
        + " WHERE "
        + Transaction.MONTH
        + " = ? AND "
        + Transaction.YEAR
        + " = ? "
        + " GROUP BY "
        + Container.ID
        + " ORDER BY "
        + Container.TITLE, String.valueOf(month), String.valueOf(year))
        .map(super::getCursor)
        .map(cursor -> {
          try {
            ArrayList<PresentationBudget> budgets = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
              PresentationBudget budget =
                  new PresentationBudget(DbUtil.getLong(cursor, Container.ID),
                      DbUtil.getString(cursor, Container.TITLE),
                      DbUtil.getDouble(cursor, Container.VALUE), DbUtil.getDouble(cursor, OUT));
              budgets.add(budget);
            }
            return budgets;
          } finally {
            cursor.close();
          }
        });
  }

  public Observable<ArrayList<PresentationHistory>> getHistory() {
    return db.createQuery(TABLE_TRANSACTION, "SELECT SUM("
        + Transaction.VALUE
        + ") AS "
        + OUT
        + ", "
        + Transaction.MONTH
        + ", "
        + Transaction.YEAR
        + " FROM "
        + TABLE_TRANSACTION
        + " GROUP BY "
        + Transaction.YEAR
        + ", "
        + Transaction.MONTH
        + " ORDER BY "
        + Transaction.YEAR
        + " DESC, "
        + Transaction.MONTH
        + " DESC").map(super::getCursor).map(cursor -> {
      try {
        ArrayList<PresentationHistory> histories = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          PresentationHistory history =
              new PresentationHistory(DbUtil.getInt(cursor, Transaction.MONTH),
                  DbUtil.getInt(cursor, Transaction.YEAR), DbUtil.getDouble(cursor, OUT));
          histories.add(history);
        }
        return histories;
      } finally {
        cursor.close();
      }
    });
  }
}
