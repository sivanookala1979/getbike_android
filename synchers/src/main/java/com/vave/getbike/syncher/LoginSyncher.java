package com.vave.getbike.syncher;


import com.vave.getbike.dataobject.ProviderDetails;
import com.vave.getbike.dataobject.SaveResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by suzuki on 19-04-2016.
 */
public class LoginSyncher extends BaseSyncher {
    String result = "";

    public String getOtp(String mobileNumber) {
        try {
            String responce = HTTPUtils.getDataFromServer(BASE_URL + "/user_sessions/log_in_with_mobile.json?mobile_number=" + mobileNumber, "GET");
            JSONObject jsonObject = new JSONObject(responce);
            result = jsonObject.getString("status");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public SaveResult loginWithMobileNum(String mobileNumber, String otp, String countryCode) {
        SaveResult result = new SaveResult();
        try {
            String responce = HTTPUtils.getDataFromServer(BASE_URL + "/user_sessions/register_with_mobile.json?mobile_number=" + mobileNumber + "&otp=" + otp + "&country_code=" + countryCode, "GET");
            JSONObject jsonObject = new JSONObject(responce);
            if (jsonObject.has("user_access_token")) {
                result.setReferenceID(jsonObject.getString("user_access_token"));
                result.setCurrenyCode(jsonObject.getString("currency_code"));
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setStatus(jsonObject.getString("status"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public SaveResult isValidLogInDetails(ProviderDetails provider) {
        SaveResult result = new SaveResult();
        result.setSuccess(false);
        try {
            String content = HTTPUtils.getDataFromServer(BASE_URL + "user_sessions/log_in_with_email.json?email=" + enc(provider.getEmail()) + "&name=" + enc(provider.getName()) + "&phone_number" + enc(provider.getMobileNumber()), "GET");
            if (!content.startsWith("Invalid login details")) {
                JSONObject jsonObject = new JSONObject(content);
                if (jsonObject != null && jsonObject.has("user_access_token")) {
                    result.setReferenceID(jsonObject.getString("user_access_token"));
                    result.setSuccess(true);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
