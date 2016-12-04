package com.vave.getbike.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vave.getbike.R;
import com.vave.getbike.helpers.LocationDetails;
import com.vave.getbike.helpers.ToastHelper;

public class GiveRideTakeRideActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener {

    public static final int GPS_PERMISSION_REQUEST_CODE = 8;
    GoogleMap googleMap;
    private Location mCurrentLocation;
    private LocationManager locationManager;
    private ImageButton takeRide;
    private ImageButton giveRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_ride_take_ride);
        addToolbarView();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        takeRide = (ImageButton) findViewById(R.id.takeRide);
        takeRide.setOnClickListener(this);
        giveRide = (ImageButton) findViewById(R.id.giveRide);
        giveRide.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMapParam) {
        this.googleMap = googleMapParam;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    GPS_PERMISSION_REQUEST_CODE);
            return;
        }
        resetLocation();
    }

    public void resetLocation() throws SecurityException {
        mCurrentLocation = LocationDetails.getLocationOrShowToast(GiveRideTakeRideActivity.this, locationManager);

        if (googleMap != null && mCurrentLocation != null) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())).title("Start"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 16.0f));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takeRide:
                launchActivity(GiveDestinationAddressActivity.class);
                break;
            case R.id.giveRide:
                launchActivity(HailCustomerActivity.class);
                break;
        }
    }

    public void launchActivity(Class targetActivity) {
        if (LocationDetails.isValid(mCurrentLocation)) {
            Intent intent = new Intent(GiveRideTakeRideActivity.this, targetActivity);
            intent.putExtra("latitude", mCurrentLocation.getLatitude());
            intent.putExtra("longitude", mCurrentLocation.getLongitude());
            startActivity(intent);
            finish();
        } else {
            ToastHelper.gpsToast(GiveRideTakeRideActivity.this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GPS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    resetLocation();

                } else {

                }
                return;
            }
        }
    }
}
