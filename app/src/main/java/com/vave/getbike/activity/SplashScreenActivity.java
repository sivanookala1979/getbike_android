package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikePreferences;
import com.vave.getbike.syncher.BaseSyncher;

public class SplashScreenActivity extends AppCompatActivity {

    public static final long DELAY_MILLIS = 40L;
    Handler timerHandler; // global instance
    Runnable getmiRunnable; // global instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetBikePreferences.setPreferences(getApplicationContext());
        setContentView(R.layout.activity_splash_screen);

        timerHandler = new Handler();
        getmiRunnable = new Runnable() {
            @Override
            public void run() {
                if (GetBikePreferences.isLoggedIn()) {
                    BaseSyncher.setAccessToken(GetBikePreferences.getAccessToken());
                    startActivity(new Intent(SplashScreenActivity.this, RequestRideActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, LogoScreenActivity.class));
                }
                finish();
            }
        };
        timerHandler.postDelayed(getmiRunnable, DELAY_MILLIS);
    }

}
