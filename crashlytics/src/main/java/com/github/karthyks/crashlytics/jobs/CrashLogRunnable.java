package com.github.karthyks.crashlytics.jobs;


import android.content.Context;

import com.github.karthyks.crashlytics.data.CrashlyticsDB;
import com.github.karthyks.crashlytics.data.Event;
import com.github.karthyks.crashlytics.transaction.EventTransaction;
import com.github.karthyks.crashlytics.utils.CrashUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class CrashLogRunnable implements Runnable {

  private WeakReference<Context> contextWeakReference;
  private String company;
  private String username;
  private String tag;
  private String info;
  private long time;

  public CrashLogRunnable(Context context, String company, String username, String tag,
                          String info, long time) {
    contextWeakReference = new WeakReference<>(context);
    this.company = company;
    this.username = username;
    this.tag = tag;
    this.info = info;
    this.time = time;
  }

  @Override
  public void run() {
    Context context = contextWeakReference.get();
    if (context == null) return;
    android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
    Event eventModel = new Event();
    eventModel.setAppName(CrashUtils.getApplicationName(context));
    eventModel.setAppVersion(CrashUtils.getAppVersion(context));
    eventModel.setAndroidVersion(CrashUtils.getOsVersion());
    eventModel.setDeviceModel(CrashUtils.getDeviceMake());
    eventModel.setUsername(username);
    eventModel.setCompany(company);
    eventModel.setTag(tag);
    eventModel.setInfo(info);
    eventModel.setTime(time);
    pushToCloud(eventModel);
  }

  private void pushToCloud(Event eventModel) {
    List<Event> eventModelList = new LinkedList<>();
    eventModelList.add(eventModel);
    EventTransaction eventTransaction = new EventTransaction();
    try {
      eventTransaction.postEvent(eventModelList);
    } catch (Exception e) {
      saveLocal(eventModel);
      e.printStackTrace();
    }
  }

  private void saveLocal(Event eventModel) {
    Context context = contextWeakReference.get();
    if (context == null) return;
    CrashlyticsDB.get(context).eventDao().insertEvent(eventModel);
  }
}
