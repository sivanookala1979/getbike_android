package com.vave.getbike.syncher;

import com.vave.getbike.model.Ride;
import com.vave.getbike.model.RideLocation;
import com.vave.getbike.utils.GsonUtils;
import com.vave.getbike.utils.HTTPUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sivanookala on 26/10/16.
 */

public class RideSyncher extends BaseSyncher {

    public Ride requestRide(final double latitude, final double longitude) {
        return requestRide(latitude, longitude, "Source Not Provided", "Destination Not Provided");
    }

    public Ride requestRide(final double latitude, final double longitude, final String sourceAddress, final String destinationAddress) {
        final GetBikePointer<Ride> result = new GetBikePointer<>();
        new JsonPostHandler("/getBike") {

            @Override
            protected void prepareRequest() {
                put("latitude", latitude);
                put("longitude", longitude);
                put("sourceAddress", sourceAddress);
                put("destinationAddress", destinationAddress);
            }

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                Ride ride = new Ride();
                ride.setId(jsonResult.getLong("rideId"));
                result.setValue(ride);
            }
        }.handle();
        return result.getValue();
    }

    public Ride hailCustomer(final double latitude, final double longitude, final String sourceAddress, final String destinationAddress, final String phoneNumber) {
        final GetBikePointer<Ride> result = new GetBikePointer<>();
        new JsonPostHandler("/hailCustomer") {

            @Override
            protected void prepareRequest() {
                put("latitude", latitude);
                put("longitude", longitude);
                put("sourceAddress", sourceAddress);
                put("destinationAddress", destinationAddress);
                put("phoneNumber", phoneNumber);
            }

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

    public boolean rateRide(long rideId, int rating) {
        final GetBikePointer<Boolean> result = new GetBikePointer<>(null);
        result.setValue(false);
        new JsonGetHandler("/rateRide?rideId=" + rideId + "&rating=" + rating) {

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
                    Ride ride = createRideFromJson(jsonResult);
                    result.setValue(ride);
                }
            }
        }.handle();
        return result.getValue();
    }

    public Ride getCompleteRideById(long rideId, final List<RideLocation> rideLocations) {
        final GetBikePointer<Ride> result = new GetBikePointer<>(null);
        new JsonGetHandler("/getCompleteRideById?rideId=" + rideId) {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && jsonResult.get("result").equals("success")) {
                    Ride ride = createRideFromJson(jsonResult);
                    if (jsonResult.has("rideLocations")) {
                        JSONArray rideLocationsArray = jsonResult.getJSONArray("rideLocations");
                        for (int i = 0; i < rideLocationsArray.length(); i++) {
                            RideLocation rideLocation = GsonUtils.getGson().fromJson(rideLocationsArray.get(i).toString(), RideLocation.class);
                            rideLocations.add(rideLocation);
                        }
                    }
                    result.setValue(ride);
                }
            }
        }.handle();
        return result.getValue();
    }

    private Ride createRideFromJson(JSONObject jsonResult) throws JSONException {
        JSONObject jsonRideObject = (JSONObject) jsonResult.get("ride");
        Ride ride = GsonUtils.getGson().fromJson(jsonRideObject.toString(), Ride.class);
        if (jsonResult.has("requestorName") && !jsonResult.isNull("requestorName")) {
            ride.setRequestorName(jsonResult.getString("requestorName"));
        }
        ride.setRequestorPhoneNumber(jsonResult.getString("requestorPhoneNumber"));
        return ride;
    }

    public List<Ride> openRides(double latitude, double longitude) {
        final ArrayList<Ride> result = new ArrayList<>();
        new JsonGetHandler("/openRides?latitude=" + latitude + "&longitude=" + longitude) {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("rides")) {
                    JSONArray ridesArray = jsonResult.getJSONArray("rides");
                    for (int i = 0; i < ridesArray.length(); i++) {
                        result.add(createRideFromJson(ridesArray.getJSONObject(i)));
                    }
                }
            }
        }.handle();
        return result;
    }

    public List<Ride> getMyCompletedRides() {
        final ArrayList<Ride> result = new ArrayList<>();
        new JsonGetHandler("/getMyCompletedRides") {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("rides")) {
                    JSONArray ridesArray = jsonResult.getJSONArray("rides");
                    for (int i = 0; i < ridesArray.length(); i++) {
                        result.add(createRideFromJson(ridesArray.getJSONObject(i)));
                    }
                }
            }
        }.handle();
        return result;
    }

    public List<Ride> getRidesGivenByMe() {
        final ArrayList<Ride> result = new ArrayList<>();
        new JsonGetHandler("/getRidesGivenByMe") {

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("rides")) {
                    JSONArray ridesArray = jsonResult.getJSONArray("rides");
                    for (int i = 0; i < ridesArray.length(); i++) {
                        result.add(createRideFromJson(ridesArray.getJSONObject(i)));
                    }
                }
            }
        }.handle();
        return result;
    }

    public Ride estimateRide(List<RideLocation> rideLocations) {
        Ride result = null;
        try {
            JSONArray jsonArray = new JSONArray();
            int index = 0;
            for (RideLocation rideLocation : rideLocations) {
                JSONObject rideLocationObject = new JSONObject();
                rideLocationObject.put("latitude", rideLocation.getLatitude());
                rideLocationObject.put("longitude", rideLocation.getLongitude());
                jsonArray.put(index++, rideLocationObject);
            }
            String response = HTTPUtils.getDataFromServer(BASE_URL + "/estimateRide", "POST", jsonArray.toString());
            result = GsonUtils.getGson().fromJson(response, Ride.class);
        } catch (Exception ex) {
            handleException(ex);
        }
        return result;
    }

}
