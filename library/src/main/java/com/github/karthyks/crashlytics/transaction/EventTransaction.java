package com.github.karthyks.crashlytics.transaction;


import com.github.karthyks.crashlytics.Crashlytics;
import com.github.karthyks.crashlytics.model.EventModel;

import java.util.List;

public class EventTransaction {

  public EventTransaction() {

  }

  public void postEvent(List<EventModel> eventModelList) throws Exception {
    Crashlytics.customCrashEvent.onEventOccurred(eventModelList);
  }
}