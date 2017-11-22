package com.github.karthyks.crashlytics.dao;


import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

import com.github.karthyks.crashlytics.model.EventModel;
import com.github.karthyks.crashlytics.provider.LocalStoreContract;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EventDAO extends DAO<EventModel> {

  public EventDAO(String authority, ContentResolver contentResolver, Uri uri) {
    super(authority, contentResolver, uri);
  }

  @Override
  public List<EventModel> findAll() {
    Cursor cursor = contentResolver.query(uri, EventModel.PROJECTION, null, null,
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
    return eventModelList;
  }

  @Override
  public void deleteAll() {
    contentResolver.delete(uri, null, null);
  }

  @Override
  public void insertOne(EventModel model) {
    ArrayList<ContentProviderOperation> batch = new ArrayList<>();
    batch.add(ContentProviderOperation
        .newInsert(uri)
        .withValues(model.toContentValues())
        .build());
    try {
      contentResolver.applyBatch(authority, batch);
    } catch (RemoteException | OperationApplicationException e) {
      e.printStackTrace();
    }
  }
}