package com.vave.getbike.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.LocationDetails;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.syncher.LoginSyncher;

public class GiveRideTakeRideActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener  {

    public static final int GPS_PERMISSION_REQUEST_CODE = 8;
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location fusedCurrentLocation;
    GoogleMap googleMap;
    Long rideID = null;
    Button showCurrentRideButton;
    LinearLayout giveRideTakeRideLinearLayout;
    private Location mCurrentLocation;
    private LocationManager locationManager;
    private ImageButton takeRide;
    private ImageButton giveRide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        addNavigationMenu();
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.d(TAG, "onCreate ...............................");
        //show error dialog if GoolglePlayServices not available
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        showCurrentRideButton = (Button) findViewById(R.id.show_current_ride_button);
        giveRideTakeRideLinearLayout = (LinearLayout) findViewById(R.id.give_ride_take_ride_linear_layout);
        takeRide = (ImageButton) findViewById(R.id.takeRide);
        takeRide.setOnClickListener(this);
        giveRide = (ImageButton) findViewById(R.id.giveRide);
        giveRide.setOnClickListener(this);
        showCurrentRideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Tag", "clicked on show current ride button");
                Intent intent = new Intent(GiveRideTakeRideActivity.this, LocationActivity.class);
                intent.putExtra("rideId", rideID);
                startActivity(intent);
            }
        });
        updateCurrentRideId();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
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
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())).title("GPS"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 16.0f));
            googleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takeRide:
                launchActivity(GiveDestinationAddressActivity.class);
                break;
            case R.id.giveRide:
                launchActivity(OpenRidesActivity.class);
                break;
        }
    }

    public void launchActivity(Class targetActivity) {
        resetLocation();
        if (LocationDetails.isValid(mCurrentLocation)) {
            Intent intent = new Intent(GiveRideTakeRideActivity.this, targetActivity);
            intent.putExtra("latitude", mCurrentLocation.getLatitude());
            intent.putExtra("longitude", mCurrentLocation.getLongitude());
            startActivity(intent);
        } else {
            ToastHelper.gpsToast(GiveRideTakeRideActivity.this);
            LocationDetails.displayLocationSettingsRequest(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        updateCurrentRideId();
        resetLocation();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
    }

    public void updateCurrentRideId() {
        new GetBikeAsyncTask(GiveRideTakeRideActivity.this) {

            @Override
            public void process() {
                rideID = new LoginSyncher().getCurrentRide();
            }

            @Override
            public void afterPostExecute() {
                if (rideID == null) {
                    showCurrentRideButton.setVisibility(View.GONE);
                    giveRideTakeRideLinearLayout.setVisibility(View.VISIBLE);
                } else {
                    showCurrentRideButton.setVisibility(View.VISIBLE);
                    giveRideTakeRideLinearLayout.setVisibility(View.GONE);
                }
            }
        }.execute();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        fusedCurrentLocation = location;
        Log.d(TAG, "fusedCurrentLocation is:"+fusedCurrentLocation);
        if (googleMap != null && fusedCurrentLocation != null) {
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(new LatLng(fusedCurrentLocation.getLatitude(), fusedCurrentLocation.getLongitude())).title("Fused Api"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(fusedCurrentLocation.getLatitude(), fusedCurrentLocation.getLongitude()), 16.0f));
        }
    }

    protected void startLocationUpdates() {
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
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

}
