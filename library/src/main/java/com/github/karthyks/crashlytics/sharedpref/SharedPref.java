package com.github.karthyks.crashlytics.sharedpref;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class SharedPref implements SharedPrefClient {
  private String sharedPrefFileName;
  private Context context;

  /**
   * Recommended to use {@link #get(Context, String)} to create instance.
   *
   * @param name shared preference file name.
   */
  private SharedPref(@NonNull Context context, @NonNull String name) {
    this.sharedPrefFileName = name;
    this.context = context;
  }

  @CheckResult
  public static SharedPrefClient get(
      @NonNull Context context, @NonNull String sharedPrefFileName) {
    return new SharedPref(context.getApplicationContext(), sharedPrefFileName);
  }

  private SharedPreferences getSharedPreferences() {
    return context.getSharedPreferences(sharedPrefFileName, 0);
  }

  private SharedPreferences.Editor getEditablePrefs() {
    return getSharedPreferences().edit();
  }

  @Nullable private PreferenceAdapter getAdapter(Object t) {
    if (t instanceof Boolean) {
      return PreferenceAdapter.BOOLEAN_ADAPTER;
    } else if (t instanceof String) {
      return PreferenceAdapter.STRING_ADAPTER;
    } else if (t instanceof Float) {
      return PreferenceAdapter.FLOAT_ADAPTER;
    } else if (t instanceof Long) {
      return PreferenceAdapter.LONG_ADAPTER;
    } else if (t instanceof Integer) {
      return PreferenceAdapter.INT_ADAPTER;
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void putValue(@NonNull String key, @Nullable Object value) {
    SharedPreferences.Editor editor = getEditablePrefs();
    if (value == null) {
      editor.remove(key).apply();
      return;
    }
    PreferenceAdapter adapter = getAdapter(value);
    if (adapter == null) {
      throw new IllegalArgumentException("Unsupported value type "
          + value.getClass().getSimpleName());
    }
    adapter.write(key, value, editor);
    editor.apply();
  }

  @SuppressWarnings("unchecked")
  @Override
  @CheckResult
  public <T> T getValue(@NonNull String key, @NonNull T type) {
    SharedPreferences userProfile = getSharedPreferences();
    PreferenceAdapter<T> adapter = getAdapter(type);
    if (adapter == null)
      throw new IllegalArgumentException("Unsupported value type "
          + type.getClass().getSimpleName());
    if (!userProfile.contains(key))
      return type;
    return adapter.get(key, userProfile);
  }

  @Override public void clearAll() {
    getEditablePrefs().clear().apply();
  }

  @Override
  @CheckResult
  public String getPreferenceName() {
    return sharedPrefFileName;
  }
}

