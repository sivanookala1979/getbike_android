/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vave.getbike.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vave.getbike.R;
import com.vave.getbike.datasource.RideLocationDataSource;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.LocationDetails;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Ride;
import com.vave.getbike.model.RideLocation;
import com.vave.getbike.syncher.LoginSyncher;
import com.vave.getbike.syncher.RideLocationSyncher;
import com.vave.getbike.syncher.RideSyncher;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vave.getbike.activity.GiveRideTakeRideActivity.GPS_PERMISSION_REQUEST_CODE;

/**
 * Getting Location Updates.
 * <p>
 * Demonstrates how to use the Fused Location Provider API to get updates about a device's
 * location. The Fused Location Provider is part of the Google Play services location APIs.
 * <p>
 * For a simpler example that shows the use of Google Play services to fetch the last known location
 * of a device, see
 * https://github.com/googlesamples/android-play-location/tree/master/BasicLocation.
 * <p>
 * This sample uses Google Play services, but it does not require authentication. For a sample that
 * uses Google Play services for authentication, see
 * https://github.com/googlesamples/android-google-accounts/tree/master/QuickStart.
 */
public class LocationActivity extends BaseActivity implements
        android.location.LocationListener, View.OnClickListener, OnMapReadyCallback {

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    protected static final String TAG = "location-updates-sample";
    // Keys for storing activity state in the Bundle.
    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    protected final static String LOCATION_KEY = "location-key";
    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
    /**
     * Provides the entry point to Google Play services.
     */
    // protected GoogleApiClient mGoogleApiClient;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    //protected LocationRequest mLocationRequest;
    /**
     * Represents a geographical location.
     */
    protected Location mCurrentLocation;
    // UI Widgets.
    protected Button mStartUpdatesButton;
    protected Button mStopUpdatesButton;
    protected Button mCloseRideButton;
    protected TextView mLastUpdateTimeTextView;
    protected TextView mLocationCountTextView;
    protected Button callCustomerButton;
    protected Button reachedCustomerButton;
    protected LinearLayout reachCustomerPanel;
    protected LinearLayout locationTrackingPanel;
    //    protected ListView listView;
    //protected LocationAdapter adapter;
    // Labels.
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected String mLastUpdateTimeLabel;
    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    protected Boolean mRequestingLocationUpdates;
    /**
     * Time when the location was updated represented as a String.
     */
    protected String mLastUpdateTime;
    protected Date mLastUpdateTimeAsDate;
    List<RideLocation> locations = new ArrayList<>();
    LocationManager locationManager;
    Ride ride = null;
    private GoogleMap mMap;
    private long rideId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        addToolbarView();
        rideId = getIntent().getLongExtra("rideId", 0L);
        // Locate the UI widgets.
        mStartUpdatesButton = (Button) findViewById(R.id.start_updates_button);
        mStopUpdatesButton = (Button) findViewById(R.id.stop_updates_button);
        mCloseRideButton = (Button) findViewById(R.id.closeRide);
        mLastUpdateTimeTextView = (TextView) findViewById(R.id.last_update_time_text);
        mLocationCountTextView = (TextView) findViewById(R.id.locationCount);
        TextView tripIdTextView = (TextView) findViewById(R.id.tripId);
        tripIdTextView.setText("Trip ID : " + rideId);
        reachCustomerPanel = (LinearLayout) findViewById(R.id.reach_customer_panel);
        locationTrackingPanel = (LinearLayout) findViewById(R.id.location_tracking_panel);
        callCustomerButton = (Button) findViewById(R.id.call_customer_button);
        reachedCustomerButton = (Button) findViewById(R.id.reached_customer_button);
        // Set labels.
        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLastUpdateTimeLabel = getResources().getString(R.string.last_update_time_label);

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";
        mStopUpdatesButton.setOnClickListener(this);
        mCloseRideButton.setOnClickListener(this);
        callCustomerButton.setOnClickListener(this);
        reachedCustomerButton.setOnClickListener(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getIntent().getBooleanExtra("reachedCustomer", false)) {
            startTracking();
        }
    }

    /**
     * Handles the Start Updates button and requests start of location updates. Does nothing if
     * updates have already been requested.
     */
    public void startUpdatesButtonHandler(View view) {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            setButtonsEnabledState();
            startLocationUpdates();
        }
    }

    /**
     * Handles the Stop Updates button, and requests removal of location updates. Does nothing if
     * updates were not previously requested.
     */
    public void stopUpdatesButtonHandler(View view) {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            setButtonsEnabledState();
            stopLocationUpdates();
        }

    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    protected void startLocationUpdates() {
        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
//        LocationServices.FusedLocationApi.requestLocationUpdates(
//                mGoogleApiClient, mLocationRequest, this);
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 3000, 2, this);
        } catch (SecurityException ex) {
            ex.printStackTrace();
            ToastHelper.redToast(getApplicationContext(), "Could not request location updates");
        }
    }

    /**
     * Ensures that only one button is enabled at any time. The Start Updates button is enabled
     * if the user is not requesting location updates. The Stop Updates button is enabled if the
     * user is requesting location updates.
     */
    private void setButtonsEnabledState() {
        if (mRequestingLocationUpdates) {
            mStartUpdatesButton.setVisibility(View.GONE);
            mStopUpdatesButton.setVisibility(View.VISIBLE);
        } else {
            mStopUpdatesButton.setVisibility(View.GONE);
            mCloseRideButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Updates the latitude, the longitude, and the last location time in the UI.
     */
    private void updateUI() {
        if (mCurrentLocation != null) {
            String locationTime = String.format("%s: %s", mLastUpdateTimeLabel, mLastUpdateTime);
            mLastUpdateTimeTextView.setText(locationTime);
            RideLocationDataSource dataSource = new RideLocationDataSource(getApplicationContext());
            dataSource.setUpdataSource();
            dataSource.insert(rideId, mLastUpdateTimeAsDate, mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), false);
            locations = dataSource.getRideLocations(rideId);
            dataSource.close();
            postLastKnownLocation();

            if (mMap != null) {
                PolylineOptions polylineOptions = new PolylineOptions();
                LatLng[] latLngs = new LatLng[locations.size()];
                int i = 0;
                for (RideLocation location : locations) {
                    latLngs[i] = new LatLng(location.getLatitude(), location.getLongitude());
                    i++;
                }

                mMap.addPolyline(polylineOptions
                        .add(latLngs)
                        .width(5)
                        .color(Color.RED));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 18.0f);
                mMap.animateCamera(cameraUpdate);
            }
            mLocationCountTextView.setText("Number Of Locations : " + locations.size());
        }
    }

    private void postLastKnownLocation() {
        if (locations.size() > 0 && locations.size() % 10 == 0) {
            new GetBikeAsyncTask(LocationActivity.this) {

                @Override
                public void process() {
                    LoginSyncher loginSyncher = new LoginSyncher();
                    loginSyncher.storeLastKnownLocation(new Date(), mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                }

                @Override
                public void afterPostExecute() {
                }
            }.execute();
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.

        // The final argument to {@code requestLocationUpdates()} is a LocationListener
        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
        // LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        Log.i(TAG, "Connected to GoogleApiClient");
//
//        // If the initial location was never previously requested, we use
//        // FusedLocationApi.getLastLocation() to get it. If it was previously requested, we store
//        // its value in the Bundle and check for it in onCreate(). We
//        // do not request it again unless the user specifically requests location updates by pressing
//        // the Start Updates button.
//        //
//        // Because we cache the value of the initial location in the Bundle, it means that if the
//        // user launches the activity,
//        // moves to a new location, and then changes the device orientation, the original location
//        // is displayed as the activity is re-created.
//        if (mCurrentLocation == null) {
//            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//            mLastUpdateTimeAsDate = new Date();
//            mLastUpdateTime = DateFormat.getTimeInstance().format(mLastUpdateTimeAsDate);
//            updateUI();
//        }
//
//        // If the user presses the Start Updates button before GoogleApiClient connects, we set
//        // mRequestingLocationUpdates to true (see startUpdatesButtonHandler()). Here, we check
//        // the value of mRequestingLocationUpdates and if it is true, we start location updates.
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
//    }

    /**
     * Callback that fires when the location changes.
     */
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTimeAsDate = new Date();
        mLastUpdateTime = DateFormat.getTimeInstance().format(mLastUpdateTimeAsDate);
        updateUI();
        Toast.makeText(this, getResources().getString(R.string.location_updated_message),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stop_updates_button:
                stopUpdatesButtonHandler(v);
                new GetBikeAsyncTask(LocationActivity.this) {

                    @Override
                    public void process() {
                        RideLocationDataSource dataSource = new RideLocationDataSource(getApplicationContext());
                        dataSource.setUpdataSource();
                        RideLocationSyncher locationSyncher = new RideLocationSyncher();
                        locationSyncher.setDataSource(dataSource);
                        RideSyncher rideSyncher = new RideSyncher();
                        locationSyncher.storePendingLocations(rideId);
                        dataSource.close();
                    }

                    @Override
                    public void afterPostExecute() {
                    }
                }.execute();
                break;
            case R.id.closeRide:
                new GetBikeAsyncTask(LocationActivity.this) {
                    Ride closedRide = null;

                    @Override
                    public void process() {
                        RideSyncher sut = new RideSyncher();
                        closedRide = sut.closeRide(rideId);
                    }

                    @Override
                    public void afterPostExecute() {
                        if (closedRide != null) {
                            Intent intent = new Intent(LocationActivity.this, ShowCompletedRideActivity.class);
                            intent.putExtra("rideId", closedRide.getId());
                            startActivity(intent);
                            finish();
                        }
                    }
                }.execute();
                break;
            case R.id.call_customer_button:
                if (ride == null || ride.getRequestorPhoneNumber() == null) {
                    ToastHelper.redToast(getApplicationContext(), "Ride is not loaded. This is an error. Please try restarting the application.");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ride.getRequestorPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;
            case R.id.reached_customer_button:
                startTracking();
                break;
        }
    }

    public void startTracking() {
        reachCustomerPanel.setVisibility(View.GONE);
        locationTrackingPanel.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    GPS_PERMISSION_REQUEST_CODE);
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        animateToLocation();
    }

    public void animateToLocation() throws SecurityException {
        Location startLocation = LocationDetails.getLocationOrShowToast(LocationActivity.this, locationManager);
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            if (LocationDetails.isValid(startLocation)) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(startLocation.getLatitude(), startLocation.getLongitude()), 16.0f));
            }
            new GetBikeAsyncTask(LocationActivity.this) {

                @Override
                public void process() {
                    RideSyncher rideSyncher = new RideSyncher();
                    ride = rideSyncher.getRideById(rideId);
                }

                @Override
                public void afterPostExecute() {
                    if (ride != null && ride.getStartLatitude() != null && ride.getStartLongitude() != null) {
                        if (ride.getStartLongitude() != 0.0 && ride.getStartLatitude() != 0.0) {
                            LatLng destination = new LatLng(ride.getStartLatitude(), ride.getStartLongitude());
                            mMap.addMarker(new MarkerOptions().position(destination).title("Customer Location"));
                        }
                    }
                }
            }.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GPS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    animateToLocation();

                } else {

                }
                return;
            }
        }
    }

}