package com.vave.getbike.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vave.getbike.R;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.RideSyncher;

import java.util.List;

public class MyCompletedRidesActivity extends AppCompatActivity {

    // UI Widgets
    ListView myCompletedRidesListView;
    List<Ride> result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_completed_rides);
        myCompletedRidesListView = (ListView) findViewById(R.id.myCompletedRides);
        new GetBikeAsyncTask(MyCompletedRidesActivity.this) {

            @Override
            public void process() {
                RideSyncher rideSyncher = new RideSyncher();
                result = rideSyncher.getMyCompletedRides();
            }

            @Override
            public void afterPostExecute() {
                if (result != null) {
                    myCompletedRidesListView.setAdapter(new ArrayAdapter<Ride>(MyCompletedRidesActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, result));
                }
            }
        }.execute();
    }
}
