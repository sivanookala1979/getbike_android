package com.vave.getbike.model;

/**
 * Created by Ramkoti martha on 11/26/2016.
 */

public class SearchResults {
    private String rideId="";
    private String requesterName="";
    private String requestedTime="";

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
