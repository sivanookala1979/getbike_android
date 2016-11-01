package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.syncher.LoginSyncher;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_otp:
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
                break;
        }
    }
}
