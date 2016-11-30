package com.vave.getbike.model;

/**
 * Created by Ramkoti martha on 11/26/2016.
 */

public class SearchResults {

    // TODO: 30/11/16 This class is not needed. We need to remove this and use ride where ever required 
    private String rideId = "";
    private String requesterName = "";
    private String requestedTime = "";

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(String requestedTime) {
        this.requestedTime = requestedTime;
    }
}
