package com.vave.getbike.syncher;

import org.json.JSONObject;

/**
 * Created by sivanookala on 25/10/16.
 */

public class LoginSyncher extends BaseSyncher {

    public Integer signup(final String name, final String mobileNumber, final String email, final char gender) {
        final GetBikePointer<Integer> result = new GetBikePointer<>(null);
        new JsonPostHandler("/signup") {

            @Override
            protected void prepareRequest() {
                put("name", name);
                put("mobileNumber", mobileNumber);
                put("email", email);
                put("gender", gender);
            }

            @Override
            protected void processResult(JSONObject jsonResult) throws Exception {
                if (jsonResult.has("id")) {
                    result.setValue((Integer) jsonResult.get("id"));
                }
            }
        }.handle();
        return result.getValue();
    }
}
