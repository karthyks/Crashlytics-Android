package com.github.karthyks.crashlytics.provider;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CrashlyticsDBHelper extends SQLiteOpenHelper {
  private static final int DATABASE_VERSION = 1;
  private static final String DATABASE_NAME = "crashlytics.db";

  private String[] createQueries;
  private String[] dropQueries;

  public CrashlyticsDBHelper(Context context, String[] createQueries, String[] dropQueries) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.createQueries = createQueries;
    this.dropQueries = dropQueries;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    for (String createQuery : createQueries) {
      db.execSQL(createQuery);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    for (String dropQuery : dropQueries) {
      db.execSQL(dropQuery);
    }
    onCreate(db);
  }
}