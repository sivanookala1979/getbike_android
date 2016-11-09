package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.SMSListener;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.syncher.LoginSyncher;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static LoginActivity inst;
    TextView receivedOtp;
    SMSListener smsListener;

    public static LoginActivity instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button sendOtp = (Button) findViewById(R.id.send_otp);
        sendOtp.setOnClickListener(this);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        receivedOtp = (TextView) findViewById(R.id.received_otp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_otp: {
                final String mobileNumber = ((TextView) findViewById(R.id.mobile)).getText() + "";
                new GetBikeAsyncTask(LoginActivity.this) {
                    boolean result = false;

                    @Override
                    public void process() {
                        LoginSyncher loginSyncher = new LoginSyncher();
                        result = loginSyncher.login(mobileNumber);
                    }

                    @Override
                    public void afterPostExecute() {

                    }
                }.execute();
            }
            break;
            case R.id.login: {
                final String mobileNumber = ((TextView) findViewById(R.id.mobile)).getText() + "";
                final String otp = ((TextView) findViewById(R.id.received_otp)).getText() + "";
                new GetBikeAsyncTask(LoginActivity.this) {
                    boolean result = false;

                    @Override
                    public void process() {
                        final LoginSyncher loginSyncher = new LoginSyncher();
                        result = loginSyncher.loginWithOtp(mobileNumber, otp);
                        if (result) {

                            try {
                                InstanceID instanceID = InstanceID.getInstance(LoginActivity.this);
                                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                                Log.i("TAG", "GCM Registration Token: " + token);
                                loginSyncher.storeGcmCode(token);

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
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
                }.execute();

            }
            break;
        }
    }

    public void updateOtp(String receivedOtp) {
        this.receivedOtp.setText(receivedOtp);
        if (smsListener != null) {
            smsListener.smsReceived();
        }
    }

    public void setSmsListener(SMSListener smsListener) {
        this.smsListener = smsListener;
    }
}
