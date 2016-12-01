package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Profile;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.LoginSyncher;
import com.vave.getbike.syncher.RideSyncher;
import com.wang.avi.AVLoadingIndicatorView;

public class WaitForRiderAllocationActivity extends BaseActivity {

    // Active Instance
    public static WaitForRiderAllocationActivity activeInstance;
    // UI Widgets.
    TextView generatedRideId;
    TextView rideRequestedAt;
    TextView allottedRiderDetails;
    TextView rideStatus;
    AVLoadingIndicatorView avLoadingIndicatorView;
    Ride ride = null;
    private long rideId;

    public static WaitForRiderAllocationActivity instance() {
        return activeInstance;
    }

    @Override
    public void onStart() {
        super.onStart();
        activeInstance = this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activeInstance = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_rider_allocation);
        addToolbarView();
        rideId = getIntent().getLongExtra("rideId", 0L);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.waitingForRider);
        generatedRideId = (TextView) findViewById(R.id.generatedRideId);
        rideRequestedAt = (TextView) findViewById(R.id.rideRequestedAt);
        rideStatus = (TextView) findViewById(R.id.rideStatus);
        allottedRiderDetails = (TextView) findViewById(R.id.allottedRiderDetails);
        avLoadingIndicatorView.show();
        if (rideId > 0) {
            new GetBikeAsyncTask(WaitForRiderAllocationActivity.this) {

                @Override
                public void process() {
                    RideSyncher rideSyncher = new RideSyncher();
                    ride = rideSyncher.getRideById(rideId);
                }

                @Override
                public void afterPostExecute() {
                    if (ride != null) {
                        generatedRideId.setText(ride.getId() + "");
                        rideRequestedAt.setText(ride.getStartLatitude() + ", " + ride.getStartLongitude() + "\n" + ride.getSourceAddress() + "\n" + ride.getDestinationAddress());
                    } else {
                        ToastHelper.redToast(WaitForRiderAllocationActivity.this, R.string.error_ride_is_not_valid);
                    }
                }
            }.execute();
        }
    }

    public void rideAccepted(long acceptedRideId) {
        if (acceptedRideId > 0 && acceptedRideId == rideId) {
            GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(WaitForRiderAllocationActivity.this) {
                Profile riderProfile;

                @Override
                public void process() {
                    RideSyncher rideSyncher = new RideSyncher();
                    ride = rideSyncher.getRideById(rideId);
                    if (ride != null) {
                        riderProfile = new LoginSyncher().getPublicProfile(ride.getRiderId());
                    }
                }

                @Override
                public void afterPostExecute() {
                    if (ride != null && riderProfile != null) {
                        avLoadingIndicatorView.hide();
                        ToastHelper.blueToast(WaitForRiderAllocationActivity.this, "Rider is allocated to you, he will call you shortly.");
                        Intent intent = new Intent(WaitForRiderAllocationActivity.this, WaitForRiderAfterAcceptanceActivity.class);
                        intent.putExtra("rideId", ride.getId());
                        startActivity(intent);
                        finish();
                    } else {
                        ToastHelper.redToast(WaitForRiderAllocationActivity.this, R.string.error_ride_is_not_valid);
                    }
                }
            };
            asyncTask.setShowProgress(false);
            asyncTask.execute();
        }
    }
}
