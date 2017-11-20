package com.github.karthyks.crashlytics.model;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class EventModel implements Parcelable {
  public static final String COLUMN_NAME_APP_NAME = "app_name";
  public static final String COLUMN_NAME_APP_VERSION = "app_version";
  public static final String COLUMN_NAME_ANDROID_VERSION = "android_version";
  public static final String COLUMN_NAME_DEVICE_MODEL = "device_model";
  public static final String COLUMN_NAME_COMPANY = "company";
  public static final String COLUMN_NAME_USERNAME = "username";
  public static final String COLUMN_NAME_EVENT_TAG = "event_tag";
  public static final String COLUMN_NAME_EVENT_INFO = "event_info";
  public static final String COLUMN_NAME_TIME = "time";


  public static final String[] PROJECTION = new String[]{
      COLUMN_NAME_APP_NAME,
      COLUMN_NAME_APP_VERSION,
      COLUMN_NAME_ANDROID_VERSION,
      COLUMN_NAME_DEVICE_MODEL,
      COLUMN_NAME_COMPANY,
      COLUMN_NAME_USERNAME,
      COLUMN_NAME_EVENT_TAG,
      COLUMN_NAME_EVENT_INFO,
      COLUMN_NAME_TIME
  };

  private String appName;
  private String appVersion;
  private String androidVersion;
  private String deviceModel;
  private String company;
  private String username;
  private String tag;
  private String info;
  private long time;

  protected EventModel(Parcel in) {
    appName = in.readString();
    appVersion = in.readString();
    androidVersion = in.readString();
    deviceModel = in.readString();
    company = in.readString();
    username = in.readString();
    tag = in.readString();
    info = in.readString();
    time = in.readLong();
  }

  public EventModel() {

  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(appName);
    dest.writeString(appVersion);
    dest.writeString(androidVersion);
    dest.writeString(deviceModel);
    dest.writeString(company);
    dest.writeString(username);
    dest.writeString(tag);
    dest.writeString(info);
    dest.writeLong(time);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Parcelable.Creator<EventModel> CREATOR = new Parcelable.Creator<EventModel>() {
    @Override
    public EventModel createFromParcel(Parcel in) {
      return new EventModel(in);
    }

    @Override
    public EventModel[] newArray(int size) {
      return new EventModel[size];
    }
  };

  public static EventModel fromCursor(Cursor cursor) {
    EventModel event = new EventModel();
    event.appName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP_NAME));
    event.appVersion = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP_VERSION));
    event.androidVersion = cursor.getString(cursor.getColumnIndex(
        COLUMN_NAME_ANDROID_VERSION));
    event.deviceModel = cursor.getString(cursor.getColumnIndex(
        COLUMN_NAME_DEVICE_MODEL));
    event.company = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_COMPANY));
    event.username = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_USERNAME));
    event.tag = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EVENT_TAG));
    event.info = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_EVENT_INFO));
    event.time = cursor.getLong(cursor.getColumnIndex(COLUMN_NAME_TIME));
    return event;
  }

  public ContentValues toContentValues() {
    ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_NAME_APP_NAME, appName);
    contentValues.put(COLUMN_NAME_APP_VERSION, appVersion);
    contentValues.put(COLUMN_NAME_ANDROID_VERSION, androidVersion);
    contentValues.put(COLUMN_NAME_DEVICE_MODEL, deviceModel);
    contentValues.put(COLUMN_NAME_COMPANY, company);
    contentValues.put(COLUMN_NAME_USERNAME, username);
    contentValues.put(COLUMN_NAME_EVENT_TAG, tag);
    contentValues.put(COLUMN_NAME_EVENT_INFO, info);
    contentValues.put(COLUMN_NAME_TIME, time);
    return contentValues;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppName() {
    return appName;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAndroidVersion(String androidVersion) {
    this.androidVersion = androidVersion;
  }

  public String getAndroidVersion() {
    return androidVersion;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getCompany() {
    return company;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return tag;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getInfo() {
    return info;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public long getTime() {
    return time;
  }

  @Override
  public String toString() {
    return String.format(Locale.getDefault(), "App name : %s,%n App version : %s,%n "
            + "Android version : %s,%n Device Model : %s,%n Company Name : %s,%n Username : %s,%n "
            + "TAG : %s,%n Info : %s,%n time: %d%n", appName,
        appVersion, androidVersion, deviceModel, company, username, tag, info, time);
  }
}
