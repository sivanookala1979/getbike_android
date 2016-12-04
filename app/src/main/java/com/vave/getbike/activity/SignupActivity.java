package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vave.getbike.R;
import com.vave.getbike.datasource.CallStatus;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.syncher.LoginSyncher;

import java.util.regex.Pattern;

public class SignupActivity extends BaseActivity implements View.OnClickListener {

    TextView resultUserId;
    RadioGroup genderGroup;
    EditText name;
    EditText mobile;
    EditText email;
    char gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        addToolbarView();
        Button signup = (Button) findViewById(R.id.signup);
        assert signup != null;
        signup.setBackgroundResource(R.mipmap.register);
        signup.setOnClickListener(this);
        //
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);

        Button gmaps = (Button) findViewById(R.id.gmaps);
        gmaps.setOnClickListener(this);
        Button redirectButton = (Button) findViewById(R.id.redirectButton);
        redirectButton.setOnClickListener(this);
        resultUserId = (TextView) findViewById(R.id.resultUserId);
        Button login = (Button) findViewById(R.id.login);
        assert login != null;
        login.setBackgroundResource(R.mipmap.sign_in);
        login.setOnClickListener(this);
        Button requestRide = (Button) findViewById(R.id.requestRide);
        requestRide.setOnClickListener(this);
        genderGroup = (RadioGroup) findViewById(R.id.gender);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    gender = 'M';
                }
                if (checkedId == R.id.female) {
                    gender = 'F';
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup:
                name.setError(null);
                email.setError(null);
                mobile.setError(null);
                final String EMAIL_REGEX = "^(.+)@(.+)$";
                final Pattern pattern1 = Pattern.compile("[^0-9]");
                boolean patternCheck = pattern1.matcher(mobile.getText().toString()).find();
                if (name.getText().toString().length() == 0) {
                    name.setError("Required");
                    name.requestFocus();
                } else if ((email.getText().toString().length() <= 0) || (!(Pattern.matches(EMAIL_REGEX, email.getText().toString())))) {
                    email.setError("Required");
                    email.requestFocus();
                } else if ((mobile.getText().toString().length() != 10) || (patternCheck)) {
                    mobile.setError("Required 10 digit number");
                    mobile.requestFocus();
                } else if (genderGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(SignupActivity.this, "Please provide your Gender", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("selected gender is:" + gender);
                    new GetBikeAsyncTask(SignupActivity.this) {
                        CallStatus callStatus = null;

                        @Override
                        public void process() {
                            LoginSyncher sut = new LoginSyncher();
                            Log.d("TAG", "call status for signup is:" + callStatus);
                            callStatus = sut.signup(readText(R.id.name), readText(R.id.mobile), readText(R.id.email), gender);
                            Log.d("TAG", "call status for signup is:" + callStatus);
                        }

                        @Override
                        public void afterPostExecute() {
                            if (callStatus != null) {
                                if (callStatus.isSuccess()) {
                                    Log.d("TAG", "call status for signup is:" + callStatus);
                                    resultUserId.setText("Success");
                                } else if (callStatus.getErrorCode() == 9901) {
                                    Log.d("TAG", "call status for signup is:" + callStatus);
                                    ToastHelper.yellowToast(getApplicationContext(), "User already exists. Please try logging in.");
                                }
                            } else {
                                ToastHelper.serverToast(getApplicationContext());
                            }
                        }
                    }.execute();
                }

                break;
            case R.id.login: {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            break;
            case R.id.requestRide: {
                Intent intent = new Intent(this, RequestRideActivity.class);
                startActivity(intent);
            }
            break;

        }
    }

    String readText(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }
}
