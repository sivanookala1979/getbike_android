package com.vave.getbike.model;

import java.util.Date;

/**
 * Created by sivanookala on 26/10/16.
 */

public class Ride {

    Long id;
    String requestorName;
    String requestorAddress;
    String requestorPhoneNumber;
    Double startLatitude;
    Double startLongitude;
    String riderName;
    Long riderId;
    String riderPhoneNumber;
    Double orderAmount;
    Double orderDistance;
    Date requestedAt;

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

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getOrderDistance() {
        return orderDistance;
    }

    public void setOrderDistance(Double orderDistance) {
        this.orderDistance = orderDistance;
    }

    public String getRequestorAddress() {
        return requestorAddress;
    }

    public void setRequestorAddress(String requestorAddress) {
        this.requestorAddress = requestorAddress;
    }

    public Double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(Double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public Double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(Double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public String getRequestorName() {

        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getRequestorPhoneNumber() {
        return requestorPhoneNumber;
    }

    public void setRequestorPhoneNumber(String requestorPhoneNumber) {
        this.requestorPhoneNumber = requestorPhoneNumber;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }
}
