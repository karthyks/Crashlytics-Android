package com.github.karthyks.crashlytics;


import android.app.Application;

import java.io.PrintWriter;
import java.io.StringWriter;

import androidx.annotation.NonNull;

public class Crashlytics {
  public static final String EVENT_EXCEPTION = "exception";
  static final String EVENT_CRASH = "crash";
  public static final String EVENT_LOGIN = "login";

  private static CrashEvent instance;
  public static EventListener eventListener;

  public static void init(@NonNull Application application, @NonNull EventListener eventListener) {
    if (instance != null) {
      throw new IllegalStateException("Initialized more than once!");
    }
    Crashlytics.eventListener = eventListener;
    instance = new CrashEvent(application);
  }

  public static void login(String company, String username) {
    checkInstanceNotNull();
    instance.onLogin(company, username);
  }

  public static void logout() {
    checkInstanceNotNull();
    instance.onLogout();
  }

  static void logCrash(Throwable throwable) {
    checkInstanceNotNull();
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    throwable.printStackTrace(pw);
    instance.logEvent(EVENT_CRASH, sw.toString());
  }

  public static void logException(Exception exception) {
    checkInstanceNotNull();
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    exception.printStackTrace(pw);
    instance.logEvent(EVENT_EXCEPTION, sw.toString());
  }

  public static void logEvent(String event, String info) {
    checkInstanceNotNull();
    instance.logEvent(event, info);
  }

  private static void checkInstanceNotNull() {
    if (instance == null) {
      throw new InstantiationError("Must call \"init\" on Crashlytics first");
    }
  }
}
