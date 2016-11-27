package com.vave.getbike.activity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vave.getbike.R;

import java.util.List;
import java.util.Locale;

public class GiveDestinationAddressActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    TextView yourLocation;
    LatLng yourLocationLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_destination_address);
        yourLocation = (TextView) findViewById(R.id.yourLocation);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);
        yourLocationLatLng = new LatLng(latitude, longitude);
        if (latitude > 0 && longitude > 0) {
            yourLocation.setText(getCompleteAddressString(latitude, longitude));
        }
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
                if(strAdd.endsWith(", "))
                {
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
}
