package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.vave.getbike.R;

public class ProfileAndSettingsActivity extends AppCompatActivity {

    Button personalDetailsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_and_settings);
        personalDetailsButton = (Button) findViewById(R.id.personalDetails);
        personalDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileAndSettingsActivity.this, PersonalDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}
