package com.vave.getbike.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Profile;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.LoginSyncher;
import com.vave.getbike.syncher.RideSyncher;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WaitForRiderAllocationActivity extends BaseActivity {

    // Active Instance
    public static WaitForRiderAllocationActivity activeInstance;

    // UI Widgets.
    AVLoadingIndicatorView avLoadingIndicatorView;
    Ride ride = null;
    ScheduledFuture<?> future = null;
    private ScheduledExecutorService scheduler = null;
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
        if (future != null) {
            future.cancel(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_rider_allocation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        scheduler =
                Executors.newScheduledThreadPool(1);
        rideId = getIntent().getLongExtra("rideId", 0L);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.waitingForRider);
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
                    } else {
                        ToastHelper.redToast(WaitForRiderAllocationActivity.this, R.string.error_ride_is_not_valid);
                    }
                }
            }.execute();
        }

        scheduleNextReminder();
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

    private void scheduleNextReminder() {
        future = scheduler.schedule(new WaitMoreAlertForUser(), 60, TimeUnit.SECONDS);
    }

    private class WaitMoreAlertForUser implements Runnable {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isFinishing()) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(WaitForRiderAllocationActivity.this);
                        builder.setCancelable(false);
                        builder.setTitle("Trip Update");
                        builder.setMessage("We are sorry. Currently all are our riders are busy, do you want to wait for some more time?");
                        builder.setPositiveButton("Wait More", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                scheduleNextReminder();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        builder.show();
                    }
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        // do nothing.
    }
}
