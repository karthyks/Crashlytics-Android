package com.github.karthyks.crashlytics;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

final class Preconditions {
  private Preconditions() {
    throw new AssertionError("No instances");
  }

  static void assertNotNull(@Nullable Object obj, @NonNull String message) {
    if (obj == null)
      throw new NullPointerException(message);
  }

  static void assertNonEmpty(@Nullable String obj, @NonNull String message) {
    if (TextUtils.isEmpty(obj))
      throw new NullPointerException(message);
  }

  static void assertSecured(@Nullable Object obj, @NonNull String message) {
    if (obj == null)
      throw new SecurityException(message);
  }
}
