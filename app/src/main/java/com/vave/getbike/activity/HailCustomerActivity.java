package com.vave.getbike.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.vave.getbike.R;
import com.vave.getbike.datasource.CallStatus;
import com.vave.getbike.helpers.GMapV2Direction;
import com.vave.getbike.helpers.GetBikeAsyncTask;
import com.vave.getbike.helpers.GetBikeTextWatcher;
import com.vave.getbike.helpers.LocationDetails;
import com.vave.getbike.helpers.LocationSyncher;
import com.vave.getbike.helpers.ToastHelper;
import com.vave.getbike.model.Ride;
import com.vave.getbike.model.RideLocation;
import com.vave.getbike.syncher.RideSyncher;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class HailCustomerActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    GoogleMap googleMap;
    String yourLocation;
    LatLng yourLocationLatLng;
    TextView rideEstimateTextView;
    EditText customerMobileNumber;
    EditText customerName;
    EditText customerEmailId;
    char gender;
    RadioGroup genderGroup;
    AutoCompleteTextView destination;
    GMapV2Direction googleMapV2Direction;
    Button giveRideButton;
    LatLng destPosition;
    Document document;
    Polyline polyline;
    List<String> locations = new ArrayList<String>();
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hail_customer);
        customerMobileNumber = (EditText) findViewById(R.id.customerMobileNumber);
        customerName = (EditText) findViewById(R.id.customerName);
        customerEmailId = (EditText) findViewById(R.id.customerEmailId);

        destination = (AutoCompleteTextView) findViewById(R.id.destination);
        rideEstimateTextView = (TextView) findViewById(R.id.rideEstimate);
        giveRideButton = (Button) findViewById(R.id.giveRide);
        giveRideButton.setOnClickListener(this);

        genderGroup = (RadioGroup) findViewById(R.id.hailCustomerGender);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.male) {
                    gender = 'M';
                }
                if (checkedId == R.id.female) {
                    gender = 'F';
                }
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);
        yourLocationLatLng = new LatLng(latitude, longitude);
        if (latitude > 0 && longitude > 0) {
            yourLocation = LocationDetails.getCompleteAddressString(HailCustomerActivity.this,latitude, longitude);
        } else {
            Location mCurrentLocation = LocationDetails.getLocationOrShowToast(HailCustomerActivity.this, locationManager);
            locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            if (mCurrentLocation != null && mCurrentLocation.getLatitude() > 0 && mCurrentLocation.getLongitude() > 0) {
                yourLocation = LocationDetails.getCompleteAddressString(HailCustomerActivity.this,mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
            }
        }
        googleMapV2Direction = new GMapV2Direction();
        addTextChangedListener();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (googleMap != null && yourLocationLatLng != null && yourLocationLatLng.latitude != 0.0 && yourLocationLatLng.longitude != 0.0) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(yourLocationLatLng.latitude, yourLocationLatLng.longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bike_pointer)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(yourLocationLatLng, 16.0f));
        }

    }

    private void addTextChangedListener() {

        destination.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> p, View v, int pos, long id) {
                new GetBikeAsyncTask(HailCustomerActivity.this) {

                    LocationDetails fromLocationDetailsByName = null;

                    @Override
                    public void process() {
                        LocationSyncher locationSyncher = new LocationSyncher();
                        fromLocationDetailsByName = locationSyncher
                                .getLocationDetailsByNameRecursive(destination
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
        destination.addTextChangedListener(new GetBikeTextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                GetBikeAsyncTask asyncTask = new GetBikeAsyncTask(HailCustomerActivity.this) {
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
        BitmapDescriptor fromResource = BitmapDescriptorFactory
                .fromResource(isDestination ? R.mipmap.location_pointer : R.mipmap.location_pointer);
        googleMap
                .addMarker(markerOptions.icon(fromResource));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 10));
    }

    private void drawPath() {
        if (yourLocationLatLng != null && destPosition != null) {
            hideKeyBoard();
            new GetBikeAsyncTask(HailCustomerActivity.this) {
                ArrayList<LatLng> directionPoints = null;
                Ride estimatedRide = null;

                @Override
                public void process() {
                    document = googleMapV2Direction.getDocument(yourLocationLatLng,
                            destPosition, GMapV2Direction.MODE_DRIVING);
                    directionPoints = googleMapV2Direction
                            .getDirection(document);
                    RideSyncher rideSyncher = new RideSyncher();
                    ArrayList<RideLocation> rideLocations = new ArrayList<RideLocation>();
                    if (directionPoints.size() <= 20) {
                        for (LatLng directionPoint : directionPoints) {
                            RideLocation rideLocation = getRideLocation(directionPoint);
                            rideLocations.add(rideLocation);
                        }
                    } else {
                        rideLocations.add(getRideLocation(directionPoints.get(0)));
                        rideLocations.add(getRideLocation(directionPoints.get(directionPoints.size() / 4)));
                        rideLocations.add(getRideLocation(directionPoints.get(directionPoints.size() / 2)));
                        rideLocations.add(getRideLocation(directionPoints.get(directionPoints.size() * 3 / 4)));
                        rideLocations.add(getRideLocation(directionPoints.get(directionPoints.size() - 1)));
                    }
                    estimatedRide = rideSyncher.estimateRide(rideLocations);
                }

                @NonNull
                public RideLocation getRideLocation(LatLng directionPoint) {
                    RideLocation rideLocation = new RideLocation();
                    rideLocation.setLatitude(directionPoint.latitude);
                    rideLocation.setLongitude(directionPoint.longitude);
                    return rideLocation;
                }

                @Override
                public void afterPostExecute() {
                    if (estimatedRide == null) {
                        ToastHelper.redToast(HailCustomerActivity.this, "Failed to estimate ride");
                    }
                    if (polyline != null) {
                        polyline.remove();
                    }
                    if (estimatedRide != null) {
                        rideEstimateTextView.setVisibility(View.VISIBLE);
                        rideEstimateTextView.setText("Estimated ₹ " + estimatedRide.getOrderAmount() + " for " + estimatedRide.getOrderDistance() + " km");

                        PolylineOptions rectLine = new PolylineOptions().width(3)
                                .color(Color.RED);
                        for (int i = 0; i < directionPoints.size(); i++) {
                            rectLine.add(directionPoints.get(i));
                        }
                        polyline = googleMap.addPolyline(rectLine);
                        polyline.setColor(Color.parseColor("#FFA500"));
                        polyline.setWidth(10f);
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();

                        builder.include(yourLocationLatLng);
                        builder.include(destPosition);
                        LatLngBounds bounds = builder.build();
                        int padding = 100;
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        googleMap.moveCamera(cu);
                    }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.giveRide:
                final Pattern pattern1 = Pattern.compile("[^0-9]");
                final Pattern pattern2 = Pattern.compile("[^a-z A-Z]");
                final Boolean stringCheck1 = pattern2.matcher(customerName.getText().toString()).find();
                boolean patternCheck = pattern1.matcher(customerMobileNumber.getText().toString()).find();
                if ((customerMobileNumber.getText().toString().length() != 10) || (patternCheck)) {
                    customerMobileNumber.setError("Required 10 digits number");
                    customerMobileNumber.requestFocus();
                } else if ((customerName.getText().toString().length() == 0) || (stringCheck1)) {
                    customerName.setError("Required only alphabets");
                    customerName.requestFocus();
                } else if (genderGroup.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(HailCustomerActivity.this, "Please select Gender", Toast.LENGTH_LONG).show();
                } else {
                    final String email;
                    email = (customerEmailId.getText().toString().length() == 0) ? "NA" : customerEmailId.getText().toString();
                    new GetBikeAsyncTask(HailCustomerActivity.this) {
                        CallStatus callStatus = null;

                        @Override
                        public void process() {
                            RideSyncher sut = new RideSyncher();
                            callStatus = sut.hailCustomer(yourLocationLatLng.latitude, yourLocationLatLng.longitude, yourLocation, destination.getText().toString(), customerMobileNumber.getText().toString(), customerName.getText().toString(), email, gender, "Cash");
                        }

                        @Override
                        public void afterPostExecute() {
                            if (callStatus.isSuccess()) {
                                Intent intent = new Intent(HailCustomerActivity.this, LocationActivity.class);
                                intent.putExtra("rideId", callStatus.getId());
                                intent.putExtra("reachedCustomer", true);
                                startActivity(intent);
                                finish();
                            } else {
                                String message = "Internal error occurred.";
                                String title = "Unknown error";
                                if (callStatus.getErrorCode() == 9905) {
                                    message = "Please fill your Rider Profile to start the ride, or wait for admin approval.";
                                    title = "Rider Profile";
                                } else if (callStatus.getErrorCode() == 9907) {
                                    message = "Please add money to your wallet.";
                                    title = "Wallet";
                                }
                                final AlertDialog.Builder builder = new AlertDialog.Builder(HailCustomerActivity.this);
                                builder.setCancelable(false);
                                builder.setTitle(title);
                                builder.setMessage(message);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (callStatus != null && callStatus.getErrorCode() == 9905) {
                                            Intent intent = new Intent(HailCustomerActivity.this, RiderProfileActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (callStatus != null && callStatus.getErrorCode() == 9907) {
                                            Intent intent = new Intent(HailCustomerActivity.this, GetBikeWalletHome.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(HailCustomerActivity.this, OpenRidesActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        }
                    }.execute();
                }
                break;
        }
    }
}
