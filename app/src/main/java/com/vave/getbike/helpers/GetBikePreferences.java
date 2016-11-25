package com.vave.getbike.helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sivanookala on 25/11/16.
 */

public class GetBikePreferences {

    private static final String LOGGED_IN = "loggedIn";
    private static final String ACCESS_TOKEN = "accessToken";
    public static SharedPreferences preferences;

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setPreferences(Context context) {
        if (preferences == null) {
            GetBikePreferences.preferences = context.getSharedPreferences(
                    "com.vave.getbike", Context.MODE_PRIVATE);
        }
    }

    public static void reset() {
        preferences.edit().clear().commit();
    }

    public static String getAccessToken() {
        return preferences.getString(ACCESS_TOKEN, "");
    }

    public static void setAccessToken(String accessToken) {
        preferences.edit().putString(ACCESS_TOKEN, accessToken).commit();
    }

    public static boolean isLoggedIn() {
        return preferences.getBoolean(LOGGED_IN, false);
    }

    public static void setLoggedIn(boolean loggedIn) {
        preferences.edit().putBoolean(LOGGED_IN, loggedIn).commit();
    }

}
