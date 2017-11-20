package com.github.karthyks.sampleapp;


import android.app.Application;

import com.github.karthyks.crashlytics.Crashlytics;

public class SampleApp extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Crashlytics.init(this, new CrashHandler());
    Crashlytics.login("org_name", "12345");
  }
}
