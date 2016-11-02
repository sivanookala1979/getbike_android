package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.syncher.LoginSyncher;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button sendOtp = (Button) findViewById(R.id.send_otp);
        sendOtp.setOnClickListener(this);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_otp: {
                final String mobileNumber = ((TextView) findViewById(R.id.mobile)).getText() + "";
                GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(getApplicationContext()) {
                    boolean result = false;

                    @Override
                    public void process() {
                        LoginSyncher loginSyncher = new LoginSyncher();
                        result = loginSyncher.login(mobileNumber);
                    }

                    @Override
                    public void afterPostExecute() {

                    }
                };
                asyncTask.setShowProgress(false);
                asyncTask.execute();
            }
            break;
            case R.id.login: {
                final String mobileNumber = ((TextView) findViewById(R.id.mobile)).getText() + "";
                final String otp = ((TextView) findViewById(R.id.received_otp)).getText() + "";
                GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(getApplicationContext()) {
                    boolean result = false;

                    @Override
                    public void process() {
                        LoginSyncher loginSyncher = new LoginSyncher();
                        result = loginSyncher.loginWithOtp(mobileNumber, otp);
                    }

                    @Override
                    public void afterPostExecute() {
                        if (result) {
                            Intent intent = new Intent(getApplicationContext(), RequestRideActivity.class);
                            startActivity(intent);
                        } else {
                            ToastHelper.redToast(getApplicationContext(), "Failed to login.");
                        }

                    }
                };
                asyncTask.setShowProgress(false);
                asyncTask.execute();
            }
            break;
        }
    }
}
