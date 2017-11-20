package com.github.karthyks.crashlytics.services;


import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.karthyks.crashlytics.model.EventModel;
import com.github.karthyks.crashlytics.provider.LocalStoreContract;
import com.github.karthyks.crashlytics.transaction.EventTransaction;
import com.github.karthyks.crashlytics.utils.CrashUtils;

import java.util.LinkedList;
import java.util.List;

public class CrashSyncIntentService extends IntentService {

  private static final String TAG = CrashSyncIntentService.class.getSimpleName();

  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   * @param name Used to name the worker thread, important only for debugging.
   */
  public CrashSyncIntentService(String name) {
    super(name);
  }

  public CrashSyncIntentService() {
    super(TAG);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    try {
      Log.d(TAG, "onHandleIntent: ");
      syncSavedEvents(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void syncSavedEvents(Context context) throws Exception {
    if (!CrashUtils.hasToSync(context)) return;
    ContentResolver contentResolver = context.getContentResolver();
    Uri eventUri = LocalStoreContract.getContentUri(LocalStoreContract.getAuthority(context),
        LocalStoreContract.EventStore.TABLE_NAME);
    Cursor cursor = contentResolver.query(eventUri, EventModel.PROJECTION, null, null,
        LocalStoreContract.EventStore._ID + " ASC");
    List<EventModel> eventModelList = new LinkedList<>();
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
      do {
        EventModel eventModel = EventModel.fromCursor(cursor);
        eventModelList.add(eventModel);
      } while (cursor.moveToNext());
      cursor.close();
    }
    if (eventModelList.size() > 0) {
      try {
        EventTransaction eventTransaction = new EventTransaction();
        eventTransaction.postEvent(eventModelList);
        contentResolver.delete(eventUri, null, null);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}