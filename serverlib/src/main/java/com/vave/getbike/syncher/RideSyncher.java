package com.vave.getbike.syncher;

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
                ride.setRideId(jsonResult.getLong("rideId"));
                result.setValue(ride);
            }
        }.handle();
        return result.getValue();
    }
}
