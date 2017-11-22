package com.github.karthyks.crashlytics.services;


import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.karthyks.crashlytics.dao.EventDAO;
import com.github.karthyks.crashlytics.model.EventModel;
import com.github.karthyks.crashlytics.provider.LocalStoreContract;
import com.github.karthyks.crashlytics.transaction.EventTransaction;
import com.github.karthyks.crashlytics.utils.CrashUtils;

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
    String authority = LocalStoreContract.getAuthority(context);
    Uri eventUri = LocalStoreContract.getContentUri(authority,
        LocalStoreContract.EventStore.TABLE_NAME);
    EventDAO eventDAO = new EventDAO(authority, contentResolver, eventUri);
    List<EventModel> eventModelList = eventDAO.findAll();
    if (eventModelList.size() > 0) {
      try {
        EventTransaction eventTransaction = new EventTransaction();
        eventTransaction.postEvent(eventModelList);
        eventDAO.deleteAll();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}