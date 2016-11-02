package com.vave.getbike.syncher;

import com.vave.getbike.datasource.CallStatus;

import org.json.JSONObject;

/**
 * Created by sivanookala on 25/10/16.
 */

public class LoginSyncher extends BaseSyncher {

    public CallStatus signup(final String name, final String mobileNumber, final String email, final char gender) {
        final GetBikePointer<CallStatus> result = new GetBikePointer<>(null);
        new JsonPostHandler("/signup") {

            @Override
            protected void prepareRequest() {
                put("name", name);
                put("phoneNumber", mobileNumber);
                put("email", email);
                put("gender", gender);
            }

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                CallStatus callStatus = new CallStatus();
                if (jsonResult.has("id")) {
                    callStatus.setId(((Integer) jsonResult.get("id")).longValue());
                    callStatus.setSuccess();
                } else {
                    callStatus.setErrorCode((Integer) jsonResult.get("errorCode"));
                }
                result.setValue(callStatus);
            }
        }.handle();
        return result.getValue();
    }

    public boolean login(final String mobileNumber) {
        final GetBikePointer<Boolean> result = new GetBikePointer<>(false);
        new JsonPostHandler("/login") {

            @Override
            protected void prepareRequest() {
                put("phoneNumber", mobileNumber);
            }

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && "success".equals(jsonResult.get("result"))) {
                    result.setValue(true);
                }
            }
        }.handle();
        return result.getValue();
    }

    public boolean loginWithOtp(final String mobileNumber, final String otpNumber) {
        final GetBikePointer<Boolean> result = new GetBikePointer<>(false);
        new JsonPostHandler("/loginWithOtp") {

            @Override
            protected void prepareRequest() {
                put("phoneNumber", mobileNumber);
                put("otp", otpNumber);
            }

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("result") && "success".equals(jsonResult.get("result"))) {
                    result.setValue(true);
                    System.out.println(jsonResult.get("authToken"));
                    BaseSyncher.setAccessToken((String)jsonResult.get("authToken"));
                }
            }
        }.handle();
        return result.getValue();
    }
}
