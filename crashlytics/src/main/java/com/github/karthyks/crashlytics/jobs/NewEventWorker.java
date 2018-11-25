package com.github.karthyks.crashlytics.jobs;

import android.content.Context;

import com.github.karthyks.crashlytics.data.CrashlyticsDB;
import com.github.karthyks.crashlytics.data.Event;
import com.github.karthyks.crashlytics.utils.CrashUtils;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NewEventWorker extends Worker {
  public static final String EXTRA_COMPANY = "company";
  public static final String EXTRA_USERNAME = "username";
  public static final String EXTRA_EVENT_TAG = "event_tag";
  public static final String EXTRA_EVENT_INFO = "event_info";


  public NewEventWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
    super(context, workerParams);
  }

  @NonNull
  @Override
  public Result doWork() {
    String company = getInputData().getString(EXTRA_COMPANY);
    String username = getInputData().getString(EXTRA_USERNAME);
    String eventTag = getInputData().getString(EXTRA_EVENT_TAG);
    String eventInfo = getInputData().getString(EXTRA_EVENT_INFO);

    Event event = new Event();
    event.setAppName(CrashUtils.getApplicationName(getApplicationContext()));
    event.setAndroidVersion(CrashUtils.getOsVersion());
    event.setAppVersion(CrashUtils.getAppVersion(getApplicationContext()));
    event.setDeviceModel(CrashUtils.getDeviceMake());
    event.setCompany(company);
    event.setUsername(username);
    event.setInfo(eventInfo);
    event.setTag(eventTag);

    try {
      CrashlyticsDB.get(getApplicationContext()).eventDao().insertEvent(event);
    } catch (Exception e) {
      return Result.RETRY;
    }
    return Result.SUCCESS;
  }
}
