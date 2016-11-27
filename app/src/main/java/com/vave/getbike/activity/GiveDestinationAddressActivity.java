package com.vave.getbike.activity;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vave.getbike.R;
import com.vave.getbike.helpers.GMapV2Direction;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.LocationDetails;
import com.vave.getbike.helpers.LocationSyncher;
import com.vave.getbike.helpers.MhkTextWatcher;
import com.vave.getbike.helpers.ToastHelper;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GiveDestinationAddressActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    TextView yourLocation;
    LatLng yourLocationLatLng;

    AutoCompleteTextView destination;
    GMapV2Direction googleMapV2Direction;
    LatLng destPosition;
    Document doc;
    Polyline polylin;
    List<String> locations = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_destination_address);
        yourLocation = (TextView) findViewById(R.id.yourLocation);
        destination = (AutoCompleteTextView) findViewById(R.id.destination);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);
        yourLocationLatLng = new LatLng(latitude, longitude);
        if (latitude > 0 && longitude > 0) {
            yourLocation.setText(getCompleteAddressString(latitude, longitude));
        }
        googleMapV2Direction = new GMapV2Direction();
        addTextChangedListener();

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                }
                strAdd = strReturnedAddress.toString();
                if (strAdd.endsWith(", ")) {
                    strAdd = strAdd.substring(0, strAdd.length() - 2);
                }
                Log.w("My Current loction", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction", "Canont get Address!");
        }
        return strAdd;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions().position(yourLocationLatLng).title("Start"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(yourLocationLatLng, 16.0f));
        }

    }

    private void addTextChangedListener() {

        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                new GetBikeAsyncTask(GiveDestinationAddressActivity.this) {

                    LocationDetails fromLocationDetailsByName = null;

                    @Override
                    public void process() {
                        LocationSyncher locationSyncher = new LocationSyncher();
                        fromLocationDetailsByName = locationSyncher
                                .getLocationDetailsByName(destination
                                        .getText().toString());
                    }

                    @Override
                    public void afterPostExecute() {
                        if (fromLocationDetailsByName != null) {
                            destPosition = new LatLng(fromLocationDetailsByName.getLatitude(), fromLocationDetailsByName.getLongitude());
                            drawMarker(destPosition, false);
                            drawPath();

                        } else {
                            ToastHelper.redToast(getApplicationContext(),
                                    "Location details not found");
                        }
                    }
                }.execute();
            }
        });
        destination.addTextChangedListener(new MhkTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(GiveDestinationAddressActivity.this) {
                    @Override
                    public void process() {
                        locations.clear();
                        String key = destination.getText().toString();
                        if (key.length() >= 3) {
                            LocationSyncher locationSyncher = new LocationSyncher(
                                    key);
                            locations = locationSyncher.getLocations();
                        }
                    }

                    @Override
                    public void afterPostExecute() {
                        String[] countries = locations
                                .toArray(new String[locations.size()]);
                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                getApplicationContext(),
                                R.layout.package_spinner_item, countries);
                        destination.setAdapter(arrayAdapter);
                    }

                };
                asyncTask.setShowProgress(false);
                asyncTask.execute();

            }
        });
    }

    private void drawMarker(LatLng point, boolean isDestination) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        int key = 1;
        BitmapDescriptor fromResource = BitmapDescriptorFactory
                .fromResource(R.drawable.pick_up_location_from_icon);
        if (isDestination) {
            key = 2;
            fromResource = BitmapDescriptorFactory
                    .fromResource(R.drawable.pick_location_to_icon);
        }
        Marker addMarker = googleMap
                .addMarker(markerOptions.icon(fromResource));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 10));
    }

    private void drawPath() {
        if (yourLocationLatLng != null && destPosition != null) {
            hideKeyBoard();
            new GetBikeAsyncTask(GiveDestinationAddressActivity.this) {
                @Override
                public void process() {
                    doc = googleMapV2Direction.getDocument(yourLocationLatLng,
                            destPosition, GMapV2Direction.MODE_DRIVING);

                }

                @Override
                public void afterPostExecute() {
                    if (polylin != null) {
                        polylin.remove();
                    }
                    ArrayList<LatLng> directionPoint = googleMapV2Direction
                            .getDirection(doc);
                    PolylineOptions rectLine = new PolylineOptions().width(3)
                            .color(Color.RED);
                    for (int i = 0; i < directionPoint.size(); i++) {
                        rectLine.add(directionPoint.get(i));
                    }
                    polylin = googleMap.addPolyline(rectLine);
                    polylin.setColor(Color.parseColor("#2a94eb"));
                    polylin.setWidth(10f);
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();

                    builder.include(yourLocationLatLng);
                    builder.include(destPosition);
                    LatLngBounds bounds = builder.build();
                    int padding = 100;
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    googleMap.moveCamera(cu);
                }
            }.execute();
        } else {
            ToastHelper.redToast(getApplicationContext(),
                    "Please select from and to locations.");
        }
    }

    public void hideKeyBoard() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
