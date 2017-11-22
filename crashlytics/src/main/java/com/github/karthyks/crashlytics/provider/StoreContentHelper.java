package com.github.karthyks.crashlytics.provider;


import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

abstract class StoreContentHelper {
  private UriMatcher mUriMatcher;
  SQLiteOpenHelper databaseHelper;
  private Context context;

  StoreContentHelper(UriMatcher uriMatcher, String authority) {
    mUriMatcher = uriMatcher;
  }

  void openConnection(Context context, SQLiteOpenHelper databaseHelper) {
    this.context = context;
    this.databaseHelper = databaseHelper;
  }

  Context getContext() {
    return context;
  }

  public abstract int[] getSupportedRoutes();

  public abstract Cursor query(Uri uri, String[] projection, String selection,
                               String[] selectionArgs,
                               String sortOrder);

  public abstract String getType(Uri uri);

  public abstract Uri insert(Uri uri, ContentValues contentValues);

  public abstract int delete(Uri uri, String selection, String[] selectionArgs);

  public abstract int update(Uri uri, ContentValues contentValues, String selection,
                             String[] selectionArgs);
}