package com.github.karthyks.sampleapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  private Button btn;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override protected void onResume() {
    super.onResume();
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        // Crash event triggered...
        btn.setText("FooBar");
      }
    }, 2000);
  }
}
