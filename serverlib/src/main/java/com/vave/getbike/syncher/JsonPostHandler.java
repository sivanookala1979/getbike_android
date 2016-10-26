package com.vave.getbike.syncher;

import com.vave.getbike.exception.GetBikeException;
import com.vave.getbike.utils.HTTPUtils;

import org.json.JSONObject;

import static com.vave.getbike.syncher.BaseSyncher.BASE_URL;
import static com.vave.getbike.syncher.BaseSyncher.handleException;

/**
 * Created by sivanookala on 26/10/16.
 */

public abstract class JsonPostHandler extends JsonHandler {

    JsonPostHandler(String url) {
        super(url);
    }

    public void handle() {
        Integer id = null;
        try {
            prepareRequest();
            String resultString = HTTPUtils.getDataFromServer(BASE_URL + url, "POST", jsonRequest.toString());
            jsonResult = new JSONObject(resultString);
            processResult(jsonResult);

        } catch (Exception e) {
            handleException(e);
        }
    }

}
