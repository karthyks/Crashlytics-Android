package com.github.karthyks.crashlytics.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Event {

  @PrimaryKey(autoGenerate = true)
  private long id;
  private String appName;
  private String appVersion;
  private String androidVersion;
  private String deviceModel;
  private String company;
  private String username;
  private String tag;
  private String info;
  private long time;

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getAndroidVersion() {
    return androidVersion;
  }

  public void setAndroidVersion(String androidVersion) {
    this.androidVersion = androidVersion;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
