package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vave.getbike.R;

public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);

        Button login_button = (Button)findViewById(R.id.login_button);
        Button signup_button = (Button) findViewById(R.id.sign_up_button);
        final View login_view=this.findViewById(R.id.login_button);
        final View sign_view=this.findViewById(R.id.sign_up_button);
        assert login_view != null;
        assert sign_view != null;
        assert login_button != null;
        assert signup_button != null;
        login_view.setBackgroundResource(R.mipmap.signin);
        sign_view.setBackgroundResource(R.mipmap.register);

        login_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DispatchActivity.this, LoginActivity.class));
                finish();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DispatchActivity.this, SignupActivity.class));
                finish();
            }
        });
    }

}
