package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.BaseSyncher;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.requestRide:
                rideID = null;
                GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(getApplicationContext()) {

                    @Override
                    public void process() {
                        RideSyncher sut = new RideSyncher();
                        //// TODO: 01/11/16 : This needs to be removed.
                        BaseSyncher.testSetup();
                        Ride ride = sut.requestRide(12.22, 87.221);
                        rideID = ride.getRideId();
                    }

                    @Override
                    public void afterPostExecute() {
                        if (rideID != null) {
                            getBikeResult.setText(rideID + "");
                        }
                    }
                };
                asyncTask.setShowProgress(false);
                asyncTask.execute();

                break;

            case R.id.startRide:
                Intent intent = new Intent(this, LocationActivity.class);
                intent.putExtra("rideId", rideID);
                startActivity(intent);
                break;
        }

    }
}
