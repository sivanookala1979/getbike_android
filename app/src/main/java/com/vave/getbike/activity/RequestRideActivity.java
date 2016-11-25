package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.iid.InstanceID;
import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.GetBikePreferences;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.RideSyncher;

public class RequestRideActivity extends AppCompatActivity implements View.OnClickListener {

    TextView getBikeResult;
    Long rideID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ride);
        Button requestRide = (Button) findViewById(R.id.requestRide);
        requestRide.setOnClickListener(this);
        getBikeResult = (TextView) findViewById(R.id.getBikeResult);
        Button location = (Button) findViewById(R.id.startRide);
        location.setOnClickListener(this);
        Button showOpenRides = (Button) findViewById(R.id.showOpenRides);
        showOpenRides.setOnClickListener(this);
        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.requestRide:
                rideID = null;
                new GetBikeAsyncTask(RequestRideActivity.this) {

                    @Override
                    public void process() {
                        RideSyncher sut = new RideSyncher();
                        Ride ride = sut.requestRide(12.22, 87.221);
                        rideID = ride.getId();
                    }

                    @Override
                    public void afterPostExecute() {
                        if (rideID != null) {
                            getBikeResult.setText(rideID + "");
                            Intent intent = new Intent(RequestRideActivity.this, WaitForRiderActivity.class);
                            intent.putExtra("rideId", rideID);
                            startActivity(intent);
                        }
                    }
                }.execute();
                break;

            case R.id.startRide:
                Intent intent = new Intent(this, LocationActivity.class);
                intent.putExtra("rideId", rideID);
                startActivity(intent);
                break;
            case R.id.showOpenRides:
                startActivity(new Intent(this, OpenRidesActivity.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this, LogoScreenActivity.class));
                try {
                    InstanceID.getInstance(getApplicationContext()).deleteInstanceID();
                } catch (Exception ex) {
                    Log.e("InstanceID", ex.getMessage(), ex);
                }
                GetBikePreferences.reset();
                finish();
                break;
        }

    }
}
