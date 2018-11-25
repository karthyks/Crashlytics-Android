package com.github.karthyks.sampleapp;


import android.app.Application;
import android.util.Log;

import com.github.karthyks.crashlytics.Crashlytics;
import com.github.karthyks.crashlytics.EventListener;
import com.github.karthyks.crashlytics.data.Event;

import java.util.List;

public class SampleApp extends Application {
  public static final String TAG = SampleApp.class.getSimpleName();

  @Override public void onCreate() {
    super.onCreate();
    Crashlytics.init(this, new EventListener() {
      @Override public void onEventOccurred(List<Event> events) throws Exception {
        // Log to your Cloud DB for future analytics.
        Log.d(TAG, "onEventOccurred: " + events.size());
      }
    });
    Crashlytics.login("org_name", "12345");
  }
}
