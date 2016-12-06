/*
 * (c) Copyright 2001-2016 COMIT AG
 * All Rights Reserved.
 */
package com.vave.getbike.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * @author Adarsh.T
 * @version 1.0, 09-Feb-2016
 */
public class LocationDetails {

    String address;
    double latitude;
    double longitude;
    String city;

    public static boolean isValid(Location location) {
        return location != null && location.getLatitude() != 0.0 && location.getLatitude() != 0.0;
    }

    public static Location getLocationOrShowToast(Context context, LocationManager locationManager) throws SecurityException {
        Location result = null;
        if (locationManager != null) {
            result = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (result == null) {
                result = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (result == null) {
                result = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            }
        }
        if (!isValid(result)) {
            ToastHelper.gpsToast(context);
        }
        return result;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
