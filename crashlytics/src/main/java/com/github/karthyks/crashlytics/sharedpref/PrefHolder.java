package com.github.karthyks.crashlytics.sharedpref;


import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

interface PrefHolder {

  /**
   * Stores the key value pair in the shared preference
   * Removes the key from the preference if {@code null} is passed as value
   *
   * @throws IllegalArgumentException if unsupported type is passed as value
   */
  void putValue(@NonNull String key, @Nullable Object value);

  /**
   * To get a stored value from the shared preferences
   *
   * @param key
   * @param type The return type of the result and the value will be taken as
   *             the default if no key or value is found in the shared prefs
   *             for the passed key
   * @return Return type is generic based on the @param type
   */
  @CheckResult <T> T getValue(@NonNull String key, @NonNull T type);

}
