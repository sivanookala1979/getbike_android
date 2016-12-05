package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.vave.getbike.R;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.SaveResult;
import com.vave.getbike.model.UserProfile;

public class PersonalDetailsActivity extends BaseActivity implements View.OnClickListener {

    EditText name, email, occupation, city, yearOfBirth, homeLocation, officeLocation, mobile;
    Button update;
    CheckBox verification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        name = (EditText) findViewById(R.id.userName);
        email = (EditText) findViewById(R.id.userEmail);
        occupation = (EditText) findViewById(R.id.userOccupation);
        city = (EditText) findViewById(R.id.userCity);
        yearOfBirth = (EditText) findViewById(R.id.userYearOfBirth);
        homeLocation = (EditText) findViewById(R.id.userHomeLocation);
        officeLocation = (EditText) findViewById(R.id.userOfficeLocation);
        mobile = (EditText) findViewById(R.id.userMobileNumber);
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        verification = (CheckBox) findViewById(R.id.verification);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update:
                UserProfile userProfile = collectData();
                SaveResult dataValid = userProfile.isDataValid();
                if(dataValid.isValid()){
                    finish();
                }else {
                    ToastHelper.redToast(getApplicationContext(),"Please enter valid "+dataValid.getErrorMessage());
                }
                break;
        }
    }

    private UserProfile collectData() {
        UserProfile userProfile = new UserProfile();
        userProfile.setName(name.getText().toString());
        userProfile.setEmail(email.getText().toString());
        userProfile.setOccupation(occupation.getText().toString());
        userProfile.setCity(city.getText().toString());
        userProfile.setYearOfBirth(yearOfBirth.getText().toString());
        userProfile.setHomeLocation(homeLocation.getText().toString());
        userProfile.setOfficeLocation(officeLocation.getText().toString());
        userProfile.setMobile(mobile.getText().toString());
        userProfile.setVerified(verification.isChecked());
        return userProfile;
    }
}
