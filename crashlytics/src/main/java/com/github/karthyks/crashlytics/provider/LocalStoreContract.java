package com.github.karthyks.crashlytics.provider;


import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_ANDROID_VERSION;
import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_APP_NAME;
import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_APP_VERSION;
import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_COMPANY;
import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_DEVICE_MODEL;
import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_EVENT_INFO;
import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_EVENT_TAG;
import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_TIME;
import static com.github.karthyks.crashlytics.model.EventModel.COLUMN_NAME_USERNAME;

public class LocalStoreContract {
  public static final String TAG = LocalStoreContract.class.getSimpleName();

  static final String PATH_EVENT = "event";

  private static final String TYPE_TEXT = " TEXT";
  private static final String TYPE_INTEGER = " INTEGER";
  private static final String CONSTRAINT_NOT_NULL = " NOT NULL";
  private static final String CONSTRAINT_UNIQUE = " UNIQUE";
  private static final String COMMA_DELIMITER = ",";

  static final String[] CREATE_TABLE_QUERIES = new String[]{
      EventStore.CREATE_QUERY
  };

  static final String[] DROP_TABLE_QUERIES = new String[]{
      EventStore.DROP_QUERY
  };

  public interface EventStore extends BaseColumns {
    String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.crashlytics.event";
    String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.crashlytics.event";
    String TABLE_NAME = "event";

    String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + _ID + TYPE_INTEGER
        + " PRIMARY KEY" + COMMA_DELIMITER
        + COLUMN_NAME_APP_NAME + TYPE_TEXT + COMMA_DELIMITER
        + COLUMN_NAME_APP_VERSION + TYPE_TEXT + COMMA_DELIMITER
        + COLUMN_NAME_ANDROID_VERSION + TYPE_TEXT + COMMA_DELIMITER
        + COLUMN_NAME_DEVICE_MODEL + TYPE_TEXT + COMMA_DELIMITER
        + COLUMN_NAME_COMPANY + TYPE_TEXT + COMMA_DELIMITER
        + COLUMN_NAME_USERNAME + TYPE_TEXT + COMMA_DELIMITER
        + COLUMN_NAME_EVENT_TAG + TYPE_TEXT + COMMA_DELIMITER
        + COLUMN_NAME_EVENT_INFO + TYPE_TEXT + COMMA_DELIMITER
        + COLUMN_NAME_TIME + TYPE_TEXT
        + " )";
    String DROP_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
  }

  public static Uri getContentUri(String authority, String tableName) {
    return Uri.parse("content://" + authority).buildUpon().appendPath(tableName)
        .appendPath("all").build();
  }

  public static String getAuthority(Context context) {
    return context.getApplicationContext().getPackageName() + ".crashlytics.localstore";
  }
}