package com.github.karthyks.crashlytics.transaction;


import com.github.karthyks.crashlytics.Crashlytics;
import com.github.karthyks.crashlytics.data.Event;

import java.util.List;

public class EventTransaction {

  public void postEvent(List<Event> eventList) throws Exception {
    Crashlytics.eventListener.onEventOccurred(eventList);
  }
}