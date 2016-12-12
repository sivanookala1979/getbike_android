package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.vave.getbike.R;

public class LogoScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Button loginButton = (Button) findViewById(R.id.login_button);
        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        final View loginView = this.findViewById(R.id.login_button);
        final View signUpView = this.findViewById(R.id.sign_up_button);
        loginView.setBackgroundResource(R.mipmap.sign_in);
        signUpView.setBackgroundResource(R.mipmap.register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LogoScreenActivity.this, LoginActivity.class));
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LogoScreenActivity.this, SignupActivity.class));
                finish();
            }
        });
    }

}
