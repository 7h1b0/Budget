package com.th1b0.budget.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DbUtil;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by 7h1b0.
 */

public final class TransactionTable extends Database {
  public TransactionTable(Context context) {
    super(context);
  }

  @Nullable public Observable<ArrayList<Transaction>> getAll(int limit) {
    return db.createQuery(TABLE_TRANSACTION, "SELECT "
        + Transaction.ID
        + ", "
        + Transaction.DAY
        + ", "
        + Transaction.MONTH
        + ","
        + Transaction.YEAR
        + ", "
        + Transaction.VALUE
        + ", "
        + Transaction.CATEGORY
        + ", "
        + Transaction.DESCRIPTION
        + " FROM "
        + TABLE_TRANSACTION
        + " ORDER BY "
        + Transaction.YEAR
        + " DESC, "
        + Transaction.MONTH
        + " DESC, "
        + Transaction.DAY
        + " DESC, "
        + Transaction.ID
        + " DESC LIMIT "
        + limit).map(super::getCursor).map(cursor -> {
      try {
        ArrayList<Transaction> transactions = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
          transactions.add(getTransaction(cursor));
        }
        return transactions;
      } finally {
        cursor.close();
      }
    });
  }

  public Observable<ArrayList<Transaction>> getAll(int month, int year) {
    return db.createQuery(TABLE_TRANSACTION, "SELECT "
        + Transaction.ID
        + ", "
        + Transaction.DAY
        + ", "
        + Transaction.MONTH
        + ","
        + Transaction.YEAR
        + ", "
        + Transaction.VALUE
        + ", "
        + Transaction.CATEGORY
        + ", "
        + Transaction.DESCRIPTION
        + " FROM "
        + TABLE_TRANSACTION
        + " WHERE "
        + Transaction.MONTH
        + " = ? AND "
        + Transaction.YEAR
        + " = ? "
        + " ORDER BY "
        + Transaction.YEAR
        + " DESC, "
        + Transaction.MONTH
        + " DESC, "
        + Transaction.DAY
        + " DESC, "
        + Transaction.ID
        + " DESC", String.valueOf(month), String.valueOf(year))
        .map(super::getCursor)
        .map(cursor -> {
          try {
            ArrayList<Transaction> transactions = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
              transactions.add(getTransaction(cursor));
            }
            return transactions;
          } finally {
            cursor.close();
          }
        });
  }

  public long add(Transaction transaction) {
    return db.insert(TABLE_TRANSACTION, getContentValues(transaction));
  }

  public int delete(Transaction transaction) {
    return db.delete(TABLE_TRANSACTION, Transaction.ID + " = ?",
        String.valueOf(transaction.getId()));
  }

  public int update(Transaction transaction) {
    return db.update(TABLE_TRANSACTION, getContentValues(transaction), Transaction.ID + " = ?",
        String.valueOf(transaction.getId()));
  }

  private ContentValues getContentValues(Transaction transaction) {
    ContentValues values = new ContentValues();
    values.put(Transaction.DAY, transaction.getDay());
    values.put(Transaction.MONTH, transaction.getMonth());
    values.put(Transaction.YEAR, transaction.getYear());
    values.put(Transaction.VALUE, transaction.getValue());
    values.put(Transaction.CATEGORY, transaction.getCategory());
    values.put(Transaction.DESCRIPTION, transaction.getDescription());

    return values;
  }

  private Transaction getTransaction(@NonNull Cursor cursor) {
    return new Transaction(DbUtil.getLong(cursor, Transaction.ID),
        DbUtil.getInt(cursor, Transaction.DAY), DbUtil.getInt(cursor, Transaction.MONTH),
        DbUtil.getInt(cursor, Transaction.YEAR),
        DbUtil.getDouble(cursor, Transaction.VALUE),
        DbUtil.getInt(cursor, Transaction.CATEGORY),
        DbUtil.getString(cursor, Transaction.DESCRIPTION));
  }
}
