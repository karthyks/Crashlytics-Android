package com.github.karthyks.crashlytics.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1, exportSchema = false)
public abstract class CrashlyticsDB extends RoomDatabase {

  public abstract EventDao eventDao();

  private static CrashlyticsDB instance;

  public static CrashlyticsDB get(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context.getApplicationContext(), CrashlyticsDB.class,
          "crashlytics.db").build();
    }
    return instance;
  }
}