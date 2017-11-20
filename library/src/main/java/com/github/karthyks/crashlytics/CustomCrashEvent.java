package com.github.karthyks.crashlytics;


import com.github.karthyks.crashlytics.model.EventModel;

import java.util.List;

public abstract class CustomCrashEvent {

  public abstract void onEventOccurred(List<EventModel> eventModels) throws Exception;
}
