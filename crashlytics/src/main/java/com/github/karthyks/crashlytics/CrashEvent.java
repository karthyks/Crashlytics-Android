package com.github.karthyks.crashlytics;


import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import com.github.karthyks.crashlytics.services.CrashLogRunnable;
import com.github.karthyks.crashlytics.services.CrashSyncIntentService;
import com.github.karthyks.crashlytics.services.CrashSyncJobService;
import com.github.karthyks.crashlytics.services.LogDataIntentService;
import com.github.karthyks.crashlytics.sharedpref.SharedPref;
import com.github.karthyks.crashlytics.sharedpref.SharedPrefClient;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

class CrashEvent {

  private static final String SHARED_PREFERENCE_NAME = "crashlytics_preferences";
  private static final String PREFERENCE_COMPANY = "pref_company";
  private static final String PREFERENCE_USERNAME = "pref_username";
  private static final int REQUEST_CRASH_SYNC = 0x011;
  private static final long ALARM_FREQUENCY = 15 * 60 * 1000;

  private Context context;

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
    this.context = application.getApplicationContext();
    sharedPrefClient = SharedPref.get(application.getApplicationContext(), SHARED_PREFERENCE_NAME);
    scheduleSync();
  }

  void logEvent(String tag, String info) {
    if (tag.equals(Crashlytics.EVENT_CRASH)) {
      CrashLogRunnable runnable = new CrashLogRunnable(context, getCompany(), getUsername(),
          tag, info, System.currentTimeMillis());
      new Thread(runnable).start();
    } else {
      context.startService(LogDataIntentService.getInstance(context, getCompany(), getUsername(),
          tag, info));
    }
  }

  void onLogin(String company, String username) {
    sharedPrefClient.putValue(PREFERENCE_COMPANY, company);
    sharedPrefClient.putValue(PREFERENCE_USERNAME, username);
  }

  void onLogout() {
    sharedPrefClient.putValue(PREFERENCE_COMPANY, "");
    sharedPrefClient.putValue(PREFERENCE_USERNAME, "");
  }

  private String getUsername() {
    return sharedPrefClient.getValue(PREFERENCE_USERNAME, "");
  }

  private String getCompany() {
    return sharedPrefClient.getValue(PREFERENCE_COMPANY, "");
  }

  private void scheduleSync() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      Intent syncIntent = new Intent(context, CrashSyncIntentService.class);
      PendingIntent pendingSyncIntent = PendingIntent.getService(context, REQUEST_CRASH_SYNC,
          syncIntent, PendingIntent.FLAG_UPDATE_CURRENT);

      AlarmManager alarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);
      alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),
          ALARM_FREQUENCY, pendingSyncIntent);
    } else {
      ComponentName syncComponent = new ComponentName(context, CrashSyncJobService.class);
      JobInfo.Builder builder = new JobInfo.Builder(REQUEST_CRASH_SYNC, syncComponent)
          .setPeriodic(ALARM_FREQUENCY)
          .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
      JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
      scheduler.schedule(builder.build());
    }
  }

  private void cancelSyncService() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      Intent syncIntent = new Intent(context, CrashSyncIntentService.class);
      PendingIntent pendingSyncIntent = PendingIntent.getService(context, REQUEST_CRASH_SYNC,
          syncIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      AlarmManager alarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);
      alarm.cancel(pendingSyncIntent);
    } else {
      JobScheduler scheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
      scheduler.cancel(REQUEST_CRASH_SYNC);
    }
  }
}