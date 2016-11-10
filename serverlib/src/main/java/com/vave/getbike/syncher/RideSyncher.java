package com.vave.getbike.syncher;

import com.google.gson.Gson;
import com.vave.getbike.model.Ride;

import org.json.JSONObject;

/**
 * Created by sivanookala on 26/10/16.
 */

public class RideSyncher extends BaseSyncher {

    public Ride requestRide(final double latitude, final double longitude) {
        final GetBikePointer<Ride> result = new GetBikePointer<>();
        new JsonGetHandler("/getBike?latitude=" + latitude + "&longitude=" + longitude) {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                Ride ride = new Ride();
                ride.setId(jsonResult.getLong("rideId"));
                result.setValue(ride);
            }
        }.handle();
        return result.getValue();
    }

    public boolean acceptRide(long rideId) {
        final GetBikePointer<Boolean> result = new GetBikePointer<>(null);
        result.setValue(false);
        new JsonGetHandler("/acceptRide?rideId=" + rideId) {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && jsonResult.get("result").equals("success")) {
                    result.setValue(true);
                }
            }
        }.handle();
        return result.getValue();
    }

    public Ride closeRide(long rideId) {
        final GetBikePointer<Ride> result = new GetBikePointer<>(null);
        new JsonGetHandler("/closeRide?rideId=" + rideId) {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && jsonResult.get("result").equals("success")) {
                    Ride ride = new Ride();
                    JSONObject jsonRideObject = (JSONObject) jsonResult.get("ride");
                    ride.setId(jsonRideObject.getLong("id"));
                    ride.setOrderDistance(jsonRideObject.getDouble("orderDistance"));
                    result.setValue(ride);
                }
            }
        }.handle();
        return result.getValue();
    }

    public Ride getRideById(long rideId) {
        final GetBikePointer<Ride> result = new GetBikePointer<>(null);
        new JsonGetHandler("/getRideById?rideId=" + rideId) {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && jsonResult.get("result").equals("success")) {
                    JSONObject jsonRideObject = (JSONObject) jsonResult.get("ride");
                    Ride ride = new Gson().fromJson(jsonRideObject.toString(), Ride.class);
                    result.setValue(ride);
                }
            }
        }.handle();
        return result.getValue();
    }
}
