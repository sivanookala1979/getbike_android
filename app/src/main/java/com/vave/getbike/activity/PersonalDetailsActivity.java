package com.vave.getbike.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.GetBikePreferences;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Profile;
import com.vave.getbike.model.SaveResult;
import com.vave.getbike.model.UserProfile;
import com.vave.getbike.syncher.LoginSyncher;
import com.vave.getbike.utils.HTTPUtils;

import java.util.ArrayList;
import java.util.List;

public class PersonalDetailsActivity extends BaseActivity implements View.OnClickListener {

    EditText name, email, occupation, city, yearOfBirth, homeLocation, officeLocation, mobile;
    Button update;
    CheckBox verification;
    LoginSyncher loginSyncher = new LoginSyncher();
    UserProfile userProfile = new UserProfile();
    ImageView profileImage;
    private static final int GALLERY_REQUET_CODE = 11111;
    private static final int CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);
        name = (EditText) findViewById(R.id.profileUserName);
        email = (EditText) findViewById(R.id.userEmail);
        occupation = (EditText) findViewById(R.id.userOccupation);
        city = (EditText) findViewById(R.id.userCity);
        yearOfBirth = (EditText) findViewById(R.id.userYearOfBirth);
        homeLocation = (EditText) findViewById(R.id.userHomeLocation);
        officeLocation = (EditText) findViewById(R.id.userOfficeLocation);
        mobile = (EditText) findViewById(R.id.userMobileNumber);
        profileImage = (ImageView)findViewById(R.id.profileImage);
        update = (Button) findViewById(R.id.update);
        update.setOnClickListener(this);
        profileImage.setOnClickListener(this);
        verification = (CheckBox) findViewById(R.id.verification);
       // getuserDetails();
    }

    private void getuserDetails() {
        new GetBikeAsyncTask(PersonalDetailsActivity.this) {

            @Override
            public void process() {
                userProfile = loginSyncher.getUserProfile();
            }

            @Override
            public void afterPostExecute() {
                if(userProfile!= null) {
                    name.setText(userProfile.getName());
                    email.setText(userProfile.getEmail());
                    occupation.setText(userProfile.getOccupation());
                    city.setText(userProfile.getCity());
                    yearOfBirth.setText(userProfile.getYearOfBirth());
                    homeLocation.setText(userProfile.getHomeLocation());
                    officeLocation.setText(userProfile.getOfficeLocation());
                    mobile.setText(userProfile.getPhoneNumber());
                    Picasso.with(getApplicationContext()).load(userProfile.getProfileImage()).placeholder(R.drawable.male_profile_icon).into(profileImage);
                    verification.setChecked(userProfile.isMobileVerified());
                }else{
                    userProfile = new UserProfile();
                }
            }
        }.execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update:
                collectData();
                SaveResult dataValid = userProfile.isDataValid();
                if (dataValid.isValid()) {
                    updateProfile(userProfile);
                } else {
                    ToastHelper.redToast(getApplicationContext(), "Please enter valid " + dataValid.getErrorMessage());
                }
                break;
            case R.id.profileImage:
                final Dialog dialog = new Dialog(PersonalDetailsActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.image_selection_dialog);
                ImageView gallery = (ImageView)dialog.findViewById(R.id.gallery);
                ImageView camera = (ImageView)dialog.findViewById(R.id.camera);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, GALLERY_REQUET_CODE);
                        dialog.cancel();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent takePicture = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, CAMERA_REQUEST_CODE);
                        dialog.cancel();
                    }
                });
                dialog.show();

                break;
        }
    }

    private void updateProfile(final UserProfile profile) {
        new GetBikeAsyncTask(PersonalDetailsActivity.this) {
            SaveResult saveResult;
            @Override
            public void process() {
                saveResult = loginSyncher.updateUserProfile(profile,profile.getProfileImage());
            }

            @Override
            public void afterPostExecute() {
                if(saveResult.isValid()){
                    ToastHelper.redToast(getApplicationContext(),"Data successfully updated.");
                }else {
                    ToastHelper.redToast(getApplicationContext(),saveResult.getErrorMessage());
                }
            }
        }.execute();
    }

    private void collectData() {
        userProfile.setName(name.getText().toString());
        userProfile.setEmail(email.getText().toString());
        userProfile.setOccupation(occupation.getText().toString());
        userProfile.setCity(city.getText().toString());
        userProfile.setYearOfBirth(yearOfBirth.getText().toString());
        userProfile.setHomeLocation(homeLocation.getText().toString());
        userProfile.setOfficeLocation(officeLocation.getText().toString());
        userProfile.setPhoneNumber(mobile.getText().toString());
        userProfile.setMobileVerified(verification.isChecked());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String customerImageBitmapToString = "";
        if (data != null) {
            Bitmap bitmap;
            if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                 customerImageBitmapToString = HTTPUtils.BitMapToString(bitmap);
                userProfile.setProfileImage(customerImageBitmapToString);
                profileImage.setImageBitmap(bitmap);
            }
            if (requestCode == GALLERY_REQUET_CODE && resultCode == Activity.RESULT_OK) {
                bitmap = HTTPUtils.getBitmapFromCameraData(data, getApplicationContext());
                customerImageBitmapToString = HTTPUtils.BitMapToString(bitmap);
                userProfile.setProfileImage(customerImageBitmapToString);
                profileImage.setImageBitmap(bitmap);
            }
        }
    }
}
