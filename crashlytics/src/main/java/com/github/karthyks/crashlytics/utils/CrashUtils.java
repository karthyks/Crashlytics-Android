package com.github.karthyks.crashlytics.utils;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class CrashUtils {
  public static String getApplicationName(Context context) {
    ApplicationInfo applicationInfo = context.getApplicationInfo();
    int stringId = applicationInfo.labelRes;
    return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString()
        : context.getString(stringId);
  }

  public static String getAppVersion(Context context) {
    String version = "";
    try {
      PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      version = pInfo.versionName + "[" + pInfo.getLongVersionCode() + "]";
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return version;
  }

  public static String getOsVersion() {
    return Build.VERSION.RELEASE;
  }

  public static String getDeviceMake() {
    return Build.MANUFACTURER + " - " + Build.MODEL;
  }
}