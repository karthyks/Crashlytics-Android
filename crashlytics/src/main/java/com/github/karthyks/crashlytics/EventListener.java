package com.github.karthyks.crashlytics;

import com.github.karthyks.crashlytics.data.Event;

import java.util.List;

public interface EventListener {

  void onEventOccurred(List<Event> events) throws Exception;
}