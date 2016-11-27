package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.RideSyncher;

import java.util.List;

public class RidesGivenByMeActivity extends AppCompatActivity {

    // UI Widgets
    ListView myCompletedRidesListView;
    List<Ride> result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_completed_rides);
        myCompletedRidesListView = (ListView) findViewById(R.id.myCompletedRides);
        new GetBikeAsyncTask(RidesGivenByMeActivity.this) {

            @Override
            public void process() {
                RideSyncher rideSyncher = new RideSyncher();
                result = rideSyncher.getRidesGivenByMe();
            }

            @Override
            public void afterPostExecute() {
                if (result != null) {
                    myCompletedRidesListView.setAdapter(new ArrayAdapter<Ride>(RidesGivenByMeActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, result));
                }
            }
        }.execute();
        myCompletedRidesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (result != null) {
                    Intent intent = new Intent(RidesGivenByMeActivity.this, ShowCompletedRideActivity.class);
                    intent.putExtra("rideId", result.get(position).getId());
                    startActivity(intent);
                }
            }
        });
    }
}
