package com.vave.getbike.model;

/**
 * Created by sivanookala on 26/10/16.
 */

public class Ride {
    Long rideId;
    String riderName;
    Long riderId;
    String riderPhoneNumber;

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public String getRiderPhoneNumber() {
        return riderPhoneNumber;
    }

    public void setRiderPhoneNumber(String riderPhoneNumber) {
        this.riderPhoneNumber = riderPhoneNumber;
    }

    public Long getRideId() {

        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }
}
