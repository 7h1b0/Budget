package com.th1b0.budget.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.th1b0.budget.model.Transaction;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

abstract class Database extends SQLiteOpenHelper {

  public static final int DATABASE_VERSION = 3;
  public static final String DATABASE_NAME = "budget";
  public static final String TABLE_TRANSACTION = "transaction_table";

  static BriteDatabase db;

  Database(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    SqlBrite sqlBrite = SqlBrite.create();
    db = sqlBrite.wrapDatabaseHelper(this, Schedulers.io());
  }

  @Override public void onCreate(SQLiteDatabase db) {
    String CREATE_TRANSACTION_TABLE = "CREATE TABLE "
        + TABLE_TRANSACTION
        + " ( "
        + Transaction.ID
        + " INTEGER PRIMARY KEY, "
        + Transaction.DAY
        + " INTEGER, "
        + Transaction.MONTH
        + " INTEGER, "
        + Transaction.YEAR
        + " INTEGER, "
        + Transaction.VALUE
        + " INTEGER, "
        + Transaction.CATEGORY
        + " INTEGER, "
        + Transaction.DESCRIPTION
        + " TEXT ) ";

    db.execSQL(CREATE_TRANSACTION_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
    onCreate(db);
  }

  Cursor getCursor(SqlBrite.Query query) {
    return query.run();
  }
}
