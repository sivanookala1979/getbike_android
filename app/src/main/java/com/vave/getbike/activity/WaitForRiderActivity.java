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

    // UI Widgets.
    TextView generatedRideId;
    TextView rideRequestedAt;
    AVLoadingIndicatorView avLoadingIndicatorView;
    Ride ride = null;

    private long rideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_rider);
        rideId = getIntent().getLongExtra("rideId", 0L);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.waitingForRider);
        generatedRideId = (TextView) findViewById(R.id.generatedRideId);
        rideRequestedAt = (TextView) findViewById(R.id.rideRequestedAt);
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
}
