package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vave.getbike.R;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signup = (Button) findViewById(R.id.signup);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                TextView resultUserId = (TextView) findViewById(R.id.resultUserId);
                resultUserId.setText("I am clicked");
                break;
        }
    }
}
