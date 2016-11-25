package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.vave.getbike.R;

public class SplashScreenActivity extends AppCompatActivity {
    Handler timerHandler; // global instance
    Runnable getmi_runnable; // global instance
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        timerHandler = new Handler();
        getmi_runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, DispatchActivity.class));
                finish();
            }
        };timerHandler.postDelayed(getmi_runnable, 4000L);
    }

}
