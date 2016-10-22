package com.vave.getbike.syncher;


import com.vave.getbike.exception.AdzShopException;
import com.vave.getbike.utils.DefaultExceptionHandler;
import com.vave.getbike.utils.ExceptionHandler;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseSyncher {
    // public static String BASE_URL = "http://getbike.cerone-software.com/";
    public static String BASE_URL = "http://localhost:9000/";
    public static String accessToken;
    public static ExceptionHandler exceptionHandler = new DefaultExceptionHandler();

    public static ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }

    public static void setExceptionHandler(ExceptionHandler exceptionHandler) {
        BaseSyncher.exceptionHandler = exceptionHandler;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        BaseSyncher.accessToken = accessToken;
    }

    public static Date StringToDate(String date) {
        Date dateTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateTime = simpleDateFormat.parse(date);
        } catch (Exception ex) {
            System.out.println("Exception " + ex);
        }
        return dateTime;
    }

    public static Date StringToDate2(String date) {
        Date dateTime = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm");
        try {
            dateTime = simpleDateFormat.parse(date);
        } catch (Exception ex) {
            System.out.println("Exception " + ex);
        }
        return dateTime;
    }

    public static String getFormatedDateFromServerFormatedDate(String serverFormattedDate) {
        return serverFormattedDate.replace('T', ' ').replace('Z', ' ').trim();
    }

    public void handleException(Exception exception) {
        exception.printStackTrace();
        exceptionHandler.handleException(exception);
    }

    public boolean isNotNull(JSONObject jsonObject, String field) {
        return !jsonObject.isNull(field);
    }

    public String enc(String input) {
        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (Exception ex) {
            handleException(ex);
        }
        throw new AdzShopException("Failed to encode string " + input);
    }
}
