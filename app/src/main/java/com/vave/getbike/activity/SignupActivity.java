package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.syncher.LoginSyncher;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(this);
        resultUserId = (TextView) findViewById(R.id.resultUserId);
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        Button location = (Button) findViewById(R.id.location);
        location.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(getApplicationContext()) {
                    Integer userID = null;

                    @Override
                    public void process() {
                        LoginSyncher sut = new LoginSyncher();
                        userID = sut.signup(readText(R.id.name), readText(R.id.mobile), readText(R.id.email), 'M');
                    }

                    @Override
                    public void afterPostExecute() {
                        if (userID != null) {
                            resultUserId.setText(userID+"");
                        }
                    }
                };
                asyncTask.setShowProgress(false);
                asyncTask.execute();
                break;
            case R.id.login:
                Intent intent = new Intent(this, RequestRideActivity.class);
                startActivity(intent);
                break;
            case R.id.location:
                startActivity(new Intent(this, LocationActivity.class));
                break;
        }
    }

    String readText(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }
}
