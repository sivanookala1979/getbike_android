package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.RideSyncher;
import com.wang.avi.AVLoadingIndicatorView;

public class WaitForRiderActivity extends AppCompatActivity {

    // Active Instance
    public static WaitForRiderActivity activeInstance;
    // UI Widgets.
    TextView generatedRideId;
    TextView rideRequestedAt;
    TextView allottedRiderId;
    AVLoadingIndicatorView avLoadingIndicatorView;
    Ride ride = null;
    private long rideId;

    public static WaitForRiderActivity instance() {
        return activeInstance;
    }

    @Override
    public void onStart() {
        super.onStart();
        activeInstance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_rider);
        rideId = getIntent().getLongExtra("rideId", 0L);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.waitingForRider);
        generatedRideId = (TextView) findViewById(R.id.generatedRideId);
        rideRequestedAt = (TextView) findViewById(R.id.rideRequestedAt);
        allottedRiderId = (TextView) findViewById(R.id.allottedRiderId);
        avLoadingIndicatorView.show();
        if (rideId > 0) {
            new GetBikeAsyncTask(WaitForRiderActivity.this) {

                @Override
                public void process() {
                    RideSyncher rideSyncher = new RideSyncher();
                    ride = rideSyncher.getRideById(rideId);
                }

                @Override
                public void afterPostExecute() {
                    if (ride != null) {
                        generatedRideId.setText(ride.getId() + "");
                        rideRequestedAt.setText(ride.getRequestedAt() + "");
                    } else {
                        ToastHelper.redToast(WaitForRiderActivity.this, R.string.error_ride_is_not_valid);
                    }
                }
            }.execute();
        }
    }

    public void rideAccepted(long acceptedRideId) {
        if (acceptedRideId > 0 && acceptedRideId == rideId) {
            GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(WaitForRiderActivity.this) {

                @Override
                public void process() {
                    RideSyncher rideSyncher = new RideSyncher();
                    ride = rideSyncher.getRideById(rideId);
                }

                @Override
                public void afterPostExecute() {
                    if (ride != null) {
                        avLoadingIndicatorView.hide();
                        allottedRiderId.setText(ride.getRiderId()+"");
                    } else {
                        ToastHelper.redToast(WaitForRiderActivity.this, R.string.error_ride_is_not_valid);
                    }
                }
            };
            asyncTask.setShowProgress(false);
            asyncTask.execute();
        }
    }
}
