package com.github.karthyks.crashlytics.provider;


import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

class EventContentHelper extends StoreContentHelper {

  private UriMatcher sUriMatcher;
  private static final int ROUTE_EVENT = 1;

  EventContentHelper(UriMatcher uriMatcher, String authority) {
    super(uriMatcher, authority);
    this.sUriMatcher = uriMatcher;
    sUriMatcher.addURI(authority, LocalStoreContract.PATH_EVENT + "/*", ROUTE_EVENT);
  }

  @Override
  public int[] getSupportedRoutes() {
    return new int[]{ROUTE_EVENT};
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                      String sortOrder) {
    int match = sUriMatcher.match(uri);
    SQLiteDatabase db = databaseHelper.getReadableDatabase();
    switch (match) {
      case ROUTE_EVENT:
        Cursor cursor = db.query(LocalStoreContract.EventStore.TABLE_NAME, projection,
            selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
      default:
        throw new UnsupportedOperationException("Unsupported URI : " + uri);
    }
  }

  @Override
  public String getType(Uri uri) {
    final int match = sUriMatcher.match(uri);
    switch (match) {
      case ROUTE_EVENT:
        return LocalStoreContract.EventStore.CONTENT_ITEM_TYPE;
      default:
        throw new UnsupportedOperationException("Unsupported URI: " + uri);
    }
  }

  @Override
  public Uri insert(Uri uri, ContentValues contentValues) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);
    switch (match) {
      case ROUTE_EVENT:
        db.insertOrThrow(LocalStoreContract.EventStore.TABLE_NAME, null, contentValues);
        break;
      default:
        throw new UnsupportedOperationException("Unsupported URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null, false);
    return uri;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);
    int result;
    switch (match) {
      case ROUTE_EVENT:
        result = db.delete(LocalStoreContract.EventStore.TABLE_NAME, selection,
            selectionArgs);
        break;
      default:
        throw new UnsupportedOperationException("Unsupported URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null, false);
    return result;
  }

  @Override
  public int update(Uri uri, ContentValues contentValues, String selection,
                    String[] selectionArgs) {
    SQLiteDatabase db = databaseHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);
    int result;
    switch (match) {
      case ROUTE_EVENT:
        result = db.update(LocalStoreContract.EventStore.TABLE_NAME, contentValues,
            selection, selectionArgs);
        break;
      default:
        throw new UnsupportedOperationException("Unsupported URI: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null, false);
    return result;
  }
}
