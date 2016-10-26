package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.datasource.RideLocationDataSource;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.RideSyncher;

import java.util.Date;

public class RequestRideActivity extends AppCompatActivity implements View.OnClickListener {

    TextView getBikeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ride);
        Button requestRide = (Button) findViewById(R.id.requestRide);
        requestRide.setOnClickListener(this);
        getBikeResult = (TextView) findViewById(R.id.getBikeResult);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.requestRide:
                GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(getApplicationContext()) {
                    Long rideID = null;

                    @Override
                    public void process() {
                        RideSyncher sut = new RideSyncher();
                        Ride ride = sut.requestRide(12.22, 87.221);
                        rideID = ride.getRideId();
                    }

                    @Override
                    public void afterPostExecute() {
                        if (rideID != null) {
                            getBikeResult.setText(rideID + "");
                        }
                        RideLocationDataSource dataSource = new RideLocationDataSource(getApplicationContext());
                        dataSource.setUpdataSource();
                        dataSource.insert(181L, new Date(), 21.45, 82.54);
                        dataSource.close();
                    }
                };
                asyncTask.setShowProgress(false);
                asyncTask.execute();

                break;
        }

    }
}
