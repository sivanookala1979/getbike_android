package com.vave.getbike.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.model.Ride;
import com.vave.getbike.model.RideLocation;
import com.vave.getbike.syncher.RideSyncher;

import java.util.ArrayList;
import java.util.List;

public class ShowCompletedRideActivity extends AppCompatActivity implements OnMapReadyCallback {

    protected TextView mTotalDistanceTextView;
    protected TextView mLocationCountTextView;

    private GoogleMap mMap;
    private Ride ride = null;
    private long rideId;
    private List<RideLocation> locations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_completed_ride);
        rideId = getIntent().getLongExtra("rideId", 0L);
        mTotalDistanceTextView = (TextView) findViewById(R.id.totalDistance);
        mLocationCountTextView = (TextView) findViewById(R.id.locationCount);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        if (rideId > 0) {
            new GetBikeAsyncTask(ShowCompletedRideActivity.this) {

                @Override
                public void process() {
                    RideSyncher rideSyncher = new RideSyncher();
                    locations.clear();
                    ride = rideSyncher.getCompleteRideById(rideId, locations);
                }

                @Override
                public void afterPostExecute() {
                    if (ride != null) {
                        mTotalDistanceTextView.setText(ride.getOrderDistance() + "");
                        mLocationCountTextView.setText(locations.size() + "");
                    }
                    if (mMap != null && rideId > 0 && locations.size() > 0) {
                        PolylineOptions polylineOptions = new PolylineOptions();
                        LatLng[] latLngs = new LatLng[locations.size()];
                        int i = 0;
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        for (RideLocation location : locations) {
                            latLngs[i] = new LatLng(location.getLatitude(), location.getLongitude());
                            builder.include(latLngs[i]);
                            i++;
                        }
                        LatLngBounds bounds = builder.build();

                        mMap.addPolyline(polylineOptions
                                .add(latLngs)
                                .width(5)
                                .color(Color.RED));
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 5);
                        mMap.animateCamera(cameraUpdate);
                    }
                }
            }.execute();
        }
    }
}
