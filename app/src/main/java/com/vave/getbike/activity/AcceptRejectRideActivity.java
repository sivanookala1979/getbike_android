package com.vave.getbike.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.GetBikePreferences;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.RideSyncher;

public class AcceptRejectRideActivity extends BaseActivity implements View.OnClickListener {

    // UI Widgets.
    TextView rideRequestedBy;
    TextView rideRequestAddress;
    TextView rideRequestLatLng;
    TextView rideRequestMobileNumber;
    TextView rideDestination;
    Button acceptRide;
    Button rejectRide;
    Button callRequestorButton;

    Ride ride = null;
    private long rideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GetBikePreferences.setPreferences(getApplicationContext());
        setContentView(R.layout.activity_accept_reject_ride);
        addToolbarView();
        rideId = getIntent().getLongExtra("rideId", 0L);
        rideRequestedBy = (TextView) findViewById(R.id.rideRequestedBy);
        rideRequestAddress = (TextView) findViewById(R.id.rideRequestAddress);
        rideRequestLatLng = (TextView) findViewById(R.id.rideRequestLatLng);
        rideRequestMobileNumber = (TextView) findViewById(R.id.rideRequestMobileNumber);
        rideDestination = (TextView) findViewById(R.id.rideDestinationAddress);
        acceptRide = (Button) findViewById(R.id.acceptRide);
        rejectRide = (Button) findViewById(R.id.rejectRide);
        callRequestorButton = (Button) findViewById(R.id.callRideRequestor);
        callRequestorButton.setOnClickListener(this);
        new GetBikeAsyncTask(AcceptRejectRideActivity.this) {

            @Override
            public void process() {
                RideSyncher rideSyncher = new RideSyncher();
                ride = rideSyncher.getRideById(rideId);
            }

            @Override
            public void afterPostExecute() {
                if (ride != null) {
                    rideRequestedBy.setText(ride.getRequestorName());
                    rideRequestAddress.setText(ride.getSourceAddress());
                    rideRequestLatLng.setText(ride.getStartLatitude() + "," + ride.getStartLongitude());
                    rideRequestMobileNumber.setText(ride.getRequestorPhoneNumber());
                    rideDestination.setText(ride.getDestinationAddress());
                } else {
                    ToastHelper.redToast(AcceptRejectRideActivity.this, R.string.error_ride_is_not_valid);
                }
            }
        }.execute();
        acceptRide.setOnClickListener(this);
        rejectRide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acceptRide:
                new GetBikeAsyncTask(AcceptRejectRideActivity.this) {
                    boolean rideAccepted = false;

                    @Override
                    public void process() {
                        RideSyncher rideSyncher = new RideSyncher();
                        rideAccepted = rideSyncher.acceptRide(rideId);
                    }

                    @Override
                    public void afterPostExecute() {
                        if (rideAccepted) {
                            Intent intent = new Intent(AcceptRejectRideActivity.this, LocationActivity.class);
                            intent.putExtra("rideId", rideId);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastHelper.redToast(AcceptRejectRideActivity.this, R.string.error_failed_to_accept_ride);
                        }
                        finish();
                    }
                }.execute();
                break;
            case R.id.rejectRide:
                finish();
                break;
            case R.id.callRideRequestor: {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ride.getRequestorPhoneNumber()));
                if (ActivityCompat.checkSelfPermission(AcceptRejectRideActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
            }
        }
    }
}
