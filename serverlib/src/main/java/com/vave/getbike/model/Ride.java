package com.vave.getbike.model;

import java.util.Date;

/**
 * Created by sivanookala on 26/10/16.
 */

public class Ride {

    Long id;
    String requestorName;
    String sourceAddress;
    String destinationAddress;
    String requestorPhoneNumber;
    Double startLatitude;
    Double startLongitude;
    String riderName;
    Long riderId;
    String riderPhoneNumber;
    Double orderAmount;
    Double orderDistance;
    Date requestedAt;
    Date rideStartedAt;
    Date rideEndedAt;
    Double totalFare;
    Double taxesAndFees;
    Double subTotal;
    Double roundingOff;
    Double totalBill;
    String rideStatus;
    Integer rating;

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

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
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

    @Override
    public String toString() {
        return "Ride Id : " + getId() + " Requestor : " + requestorName;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Date getRideStartedAt() {
        return rideStartedAt;
    }

    public void setRideStartedAt(Date rideStartedAt) {
        this.rideStartedAt = rideStartedAt;
    }

    public Date getRideEndedAt() {
        return rideEndedAt;
    }

    public void setRideEndedAt(Date rideEndedAt) {
        this.rideEndedAt = rideEndedAt;
    }

    public Double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Double totalFare) {
        this.totalFare = totalFare;
    }

    public Double getTaxesAndFees() {
        return taxesAndFees;
    }

    public void setTaxesAndFees(Double taxesAndFees) {
        this.taxesAndFees = taxesAndFees;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getRoundingOff() {
        return roundingOff;
    }

    public void setRoundingOff(Double roundingOff) {
        this.roundingOff = roundingOff;
    }

    public Double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(Double totalBill) {
        this.totalBill = totalBill;
    }

    public String getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(String rideStatus) {
        this.rideStatus = rideStatus;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
