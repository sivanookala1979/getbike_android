package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.vave.getbike.R;

public class SplashScreenActivity extends AppCompatActivity {

    Handler timerHandler; // global instance
    Runnable getmiRunnable; // global instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        timerHandler = new Handler();
        getmiRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LogoScreenActivity.class));
                finish();
            }
        };
        timerHandler.postDelayed(getmiRunnable, 4000L);
    }

}
