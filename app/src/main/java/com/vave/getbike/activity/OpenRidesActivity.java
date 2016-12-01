package com.vave.getbike.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vave.getbike.R;
import com.vave.getbike.adapter.RideAdapter2;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.model.Ride;
import com.vave.getbike.model.SearchResults;
import com.vave.getbike.syncher.RideSyncher;

import java.util.ArrayList;
import java.util.List;

public class OpenRidesActivity extends BaseActivity {

    // UI Widgets
    ListView openRidesListView;
    List<Ride> result = null;
    ArrayList<SearchResults> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_rides);
        addToolbarView();
        openRidesListView = (ListView) findViewById(R.id.openRides);

        new GetBikeAsyncTask(OpenRidesActivity.this) {

            @Override
            public void process() {
                RideSyncher rideSyncher = new RideSyncher();
                result = rideSyncher.openRides(23.45, 21.67);
            }

            @Override
            public void afterPostExecute() {
                if (result != null) {
                    searchResults = GetSearchResults();
                    openRidesListView.setAdapter(new RideAdapter2(OpenRidesActivity.this, searchResults));
                }
            }
        }.execute();
        openRidesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (result != null) {
                    Intent intent = new Intent(OpenRidesActivity.this, AcceptRejectRideActivity.class);
                    intent.putExtra("rideId", result.get(position).getId());
                    startActivity(intent);
                }
            }
        });
    }

    private ArrayList<SearchResults> GetSearchResults() {
        ArrayList<SearchResults> results = new ArrayList<SearchResults>();

        for (int i = 0; i < result.size(); i++) {
            SearchResults sr1 = new SearchResults();
            sr1.setRideId("Ride Id " + result.get(i).getId().toString());
            sr1.setRequesterName("Requestor Name " + result.get(i).getRequestorName());
            sr1.setRequestedTime("Requested Time " + "NA");
            results.add(sr1);
        }

        return results;
    }
}
