package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.RideSyncher;

public class AcceptRejectRideActivity extends AppCompatActivity implements View.OnClickListener{

    // UI Widgets.
    TextView rideRequestedBy;
    TextView rideRequestAddress;
    TextView rideRequestLatLng;
    TextView rideRequestMobileNumber;
    Button acceptRide;
    Button rejectRide;

    Ride ride = null;
    private long rideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_reject_ride);
        rideId = getIntent().getLongExtra("rideId", 0L);
        rideRequestedBy = (TextView) findViewById(R.id.rideRequestedBy);
        rideRequestAddress = (TextView) findViewById(R.id.rideRequestAddress);
        rideRequestLatLng = (TextView) findViewById(R.id.rideRequestLatLng);
        rideRequestMobileNumber = (TextView) findViewById(R.id.rideRequestMobileNumber);
        acceptRide = (Button) findViewById(R.id.acceptRide);
        rejectRide = (Button) findViewById(R.id.rejectRide);
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
                    rideRequestAddress.setText(ride.getRequestorAddress());
                    rideRequestLatLng.setText(ride.getStartLatitude() + "," + ride.getStartLongitude());
                    rideRequestMobileNumber.setText(ride.getRequestorPhoneNumber());
                } else {
                    ToastHelper.redToast(AcceptRejectRideActivity.this, R.string.error_ride_is_not_valid);
                }
            }
        }.execute();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.acceptRide:
                break;
            case R.id.rejectRide:
                break;
        }
    }
}
