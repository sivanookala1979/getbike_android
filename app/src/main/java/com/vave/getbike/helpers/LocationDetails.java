/*
 * (c) Copyright 2001-2016 COMIT AG
 * All Rights Reserved.
 */
package com.vave.getbike.helpers;

import android.location.Location;

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
