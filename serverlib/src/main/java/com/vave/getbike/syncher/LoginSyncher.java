package com.vave.getbike.syncher;

import com.vave.getbike.utils.HTTPUtils;

import org.json.JSONObject;

/**
 * Created by sivanookala on 25/10/16.
 */

public class LoginSyncher extends BaseSyncher {

    public Integer signup(String name, String mobileNumber, String email, char gender) {
        Integer id = null;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("mobileNumber", mobileNumber);
            jsonObject.put("email", email);
            jsonObject.put("gender", 'M');
            String resultString = HTTPUtils.getDataFromServer(BASE_URL + "/signup", "POST", jsonObject.toString());
            JSONObject result = new JSONObject(resultString);
            if (result.has("id")) {
                id = (Integer) result.get("id");
            }
        } catch (Exception e) {
            handleException(e);
        }
        return id;
    }


}
