package com.github.karthyks.crashlytics;


import android.app.Application;
import android.content.Context;

import com.github.karthyks.crashlytics.jobs.CrashLogRunnable;
import com.github.karthyks.crashlytics.jobs.EventSyncWorker;
import com.github.karthyks.crashlytics.jobs.NewEventWorker;
import com.github.karthyks.crashlytics.sharedpref.SharedPref;
import com.github.karthyks.crashlytics.sharedpref.SharedPrefClient;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

class CrashEvent {

  private static final String SHARED_PREFERENCE_NAME = "crashlytics_preferences";
  private static final String PREFERENCE_COMPANY = "pref_company";
  private static final String PREFERENCE_USERNAME = "pref_username";

  private WeakReference<Context> contextWeakReference;

  private SharedPrefClient sharedPrefClient;

  private static Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

  private static Thread.UncaughtExceptionHandler mCaughtExceptionHandler
      = new Thread.UncaughtExceptionHandler() {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
      Crashlytics.logCrash(ex);
      mDefaultUncaughtExceptionHandler.uncaughtException(thread, ex);
    }
  };

  CrashEvent(Application application) {
    mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(mCaughtExceptionHandler);
    this.contextWeakReference = new WeakReference<>(application.getApplicationContext());
    sharedPrefClient = SharedPref.get(contextWeakReference.get(), SHARED_PREFERENCE_NAME);
  }

  void logEvent(String tag, String info) {
    Context context = contextWeakReference.get();
    if (context == null) return;
    if (tag.equals(Crashlytics.EVENT_CRASH)) {
      CrashLogRunnable runnable = new CrashLogRunnable(context, getCompany(), getUsername(),
          tag, info, System.currentTimeMillis());
      new Thread(runnable).start();
    } else {
      OneTimeWorkRequest.Builder requestBuilder = new OneTimeWorkRequest.Builder(NewEventWorker.class)
          .setBackoffCriteria(BackoffPolicy.LINEAR,
              WorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS);
      requestBuilder.build();
      requestBuilder.setInputData(new Data.Builder()
          .putString(NewEventWorker.EXTRA_COMPANY, getCompany())
          .putString(NewEventWorker.EXTRA_USERNAME, getUsername())
          .putString(NewEventWorker.EXTRA_EVENT_TAG, tag)
          .putString(NewEventWorker.EXTRA_EVENT_INFO, info).build());
      WorkManager.getInstance().enqueue(requestBuilder.build());
    }
  }

  void onLogin(String company, String username) {
    sharedPrefClient.putValue(PREFERENCE_COMPANY, company);
    sharedPrefClient.putValue(PREFERENCE_USERNAME, username);
    scheduleSyncEvents();
  }

  void onLogout() {
    sharedPrefClient.putValue(PREFERENCE_COMPANY, "");
    sharedPrefClient.putValue(PREFERENCE_USERNAME, "");
    cancelSyncEvents();
  }

  private String getUsername() {
    return sharedPrefClient.getValue(PREFERENCE_USERNAME, "");
  }

  private String getCompany() {
    return sharedPrefClient.getValue(PREFERENCE_COMPANY, "");
  }

  private void scheduleSyncEvents() {
    Constraints constraints = new Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build();
    PeriodicWorkRequest.Builder builder = new PeriodicWorkRequest.Builder(EventSyncWorker.class,
        PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
        .setConstraints(constraints);
    builder.addTag("crashlytics_event_sync");
    builder.build();
    cancelSyncEvents();
    WorkManager.getInstance().enqueueUniquePeriodicWork("crashlytics_event_sync",
        ExistingPeriodicWorkPolicy.REPLACE, builder.build());
  }

  private void cancelSyncEvents() {
    WorkManager.getInstance().cancelAllWorkByTag("crashlytics_event_sync");
  }
}