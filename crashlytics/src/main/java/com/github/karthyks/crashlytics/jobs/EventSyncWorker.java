package com.github.karthyks.crashlytics.jobs;

import android.content.Context;

import com.github.karthyks.crashlytics.Crashlytics;
import com.github.karthyks.crashlytics.data.CrashlyticsDB;
import com.github.karthyks.crashlytics.data.Event;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class EventSyncWorker extends Worker {

  public EventSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
  }

  @NonNull
  @Override
  public Result doWork() {
    List<Event> events = CrashlyticsDB.get(getApplicationContext()).eventDao().getEvents();
    if (events != null && events.size() > 0) {
      try {
        Crashlytics.eventListener.onEventOccurred(events);
        CrashlyticsDB.get(getApplicationContext()).eventDao().deleteAll();
      } catch (Exception e) {
        e.printStackTrace();
        return Result.RETRY;
      }
    }
    return Result.SUCCESS;
  }
}