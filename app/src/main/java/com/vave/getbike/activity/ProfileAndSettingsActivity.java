package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vave.getbike.R;

public class ProfileAndSettingsActivity extends BaseActivity implements View.OnClickListener{

    Button personalDetailsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_and_settings);
        addToolbarView();
        setOnclickListeners(R.id.personalDetails,R.id.ridersProfile,R.id.bankAccountDetails,R.id.settings);
    }

    private void setOnclickListeners(int ...ids) {
        for(int id:ids){
            ImageView layout = (ImageView)findViewById(id);
            layout.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.personalDetails:
                Intent intent = new Intent(ProfileAndSettingsActivity.this,PersonalDetailsActivity.class);
                startActivity(intent);
                break;
            case R.id.ridersProfile:
                startActivity(new Intent(this, RiderProfileActivity.class));
                break;
            case R.id.bankAccountDetails:
                startActivity(new Intent(this, BankAccountDetailsActivity.class));
                break;
            case R.id.settings:
                break;
        }
    }

}
