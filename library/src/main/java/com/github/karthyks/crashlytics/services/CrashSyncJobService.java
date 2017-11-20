package com.github.karthyks.crashlytics.services;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.util.Log;

public class CrashSyncJobService extends JobService {
  public static final String TAG = CrashSyncJobService.class.getSimpleName();

  @Override
  public boolean onStartJob(JobParameters jobParameters) {
    Log.d(TAG, "onStartJob: ");
    Intent intent = new Intent(this, CrashSyncIntentService.class);
    startService(intent);
    return false;
  }

  @Override
  public boolean onStopJob(JobParameters jobParameters) {
    return false;
  }
}
