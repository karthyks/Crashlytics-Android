package com.github.karthyks.sampleapp;


import android.util.Log;

import com.github.karthyks.crashlytics.CustomCrashEvent;
import com.github.karthyks.crashlytics.model.EventModel;

import java.util.List;

public class CrashHandler extends CustomCrashEvent {
  private static final String TAG = CrashHandler.class.getSimpleName();

  @Override
  public void onEventOccurred(List<EventModel> eventModels) throws Exception {
    for (EventModel eventModel : eventModels) {
      Log.d(TAG, "onEventOccurred: " + eventModel.toString());
    }
  }
}
