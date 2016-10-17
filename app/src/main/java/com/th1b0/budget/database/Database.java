package com.th1b0.budget.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.model.Transaction;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

abstract class Database extends SQLiteOpenHelper {

  public static final int DATABASE_VERSION = 7;
  public static final String DATABASE_NAME = "budget";
  public static final String TABLE_TRANSACTION = "transaction_table";
  public static final String TABLE_CATEGORY = "category_table";
  public static final String TABLE_CONTAINER = "container_table";

  static BriteDatabase db;

  Database(@NonNull Context context) {
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
        + Transaction.ID_CATEGORY
        + " INTEGER, "
        + Transaction.DESCRIPTION
        + " TEXT ) ";

    String CREATE_CATEGORY_TABLE = "CREATE TABLE "
        + TABLE_CATEGORY
        + " ( "
        + Category.ID
        + " INTEGER PRIMARY KEY, "
        + Category.TITLE
        + " TEXT, "
        + Category.COLOR
        + " INTEGER, "
        + Category.ICON
        + " INTEGER, "
        + Category.ID_CONTAINERS
        + " INTEGER )";

    String CREATE_CONTAINER_TABLE = "CREATE TABLE "
        + TABLE_CONTAINER
        + " ( "
        + Container.ID
        + " INTEGER PRIMARY KEY, "
        + Container.TITLE
        + " TEXT, "
        + Container.VALUE
        + " INTEGER ) ";

    db.execSQL(CREATE_TRANSACTION_TABLE);
    db.execSQL(CREATE_CATEGORY_TABLE);
    db.execSQL(CREATE_CONTAINER_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTAINER);
    onCreate(db);
  }

  Cursor getCursor(@NonNull SqlBrite.Query query) {
    return query.run();
  }
}
