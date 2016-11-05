package com.th1b0.budget.database;

import android.content.Context;
import android.support.annotation.NonNull;
import com.squareup.sqlbrite.BriteDatabase;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Category;
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

public final class QueryUtil extends Database {

  public QueryUtil(@NonNull Context context) {
    super(context);
  }

  public Observable<ArrayList<PresentationBudget>> getAll(int month, int year) {
    return db.createQuery(TABLE_TRANSACTION, "SELECT SUM("
        + Transaction.VALUE
        + ") AS "
        + OUT
        + ", "
        + Budget.TITLE
        + ", "
        + Budget.VALUE
        + ", "
        + Budget.ID
        + " FROM "
        + TABLE_BUDGET
        + " JOIN "
        + TABLE_TRANSACTION
        + " ON "
        + Budget.ID
        + " = "
        + Transaction.ID_BUDGET
        + " WHERE "
        + Transaction.MONTH
        + " = ? AND "
        + Transaction.YEAR
        + " = ? "
        + " GROUP BY "
        + Budget.ID
        + " ORDER BY "
        + Budget.TITLE, String.valueOf(month), String.valueOf(year))
        .map(super::getCursor)
        .map(cursor -> {
          try {
            ArrayList<PresentationBudget> budgets = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
              PresentationBudget budget =
                  new PresentationBudget(DbUtil.getLong(cursor, Budget.ID),
                      DbUtil.getString(cursor, Budget.TITLE),
                      DbUtil.getDouble(cursor, Budget.VALUE), DbUtil.getDouble(cursor, OUT));
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

  public void initializeDatabase(ArrayList<Budget> budgets, ArrayList<Category> categories) {
    if (budgets.size() != categories.size()) {
      return;
    }

    BriteDatabase.Transaction transaction = db.newTransaction();
    try {
      for(int i =0; i < budgets.size(); i++) {
        Budget budget = budgets.get(i);
        long idBudget = db.insert(TABLE_BUDGET, getContentValues(budget));

        Category category = categories.get(i);
        category.setIdBudget(idBudget);
        db.insert(TABLE_CATEGORY, getContentValues(category));
      }
      transaction.markSuccessful();
    } finally {
      transaction.end();
    }
  }
}
