package com.vave.getbike.syncher;

import com.vave.getbike.helper.DefaultExceptionHandler;
import com.vave.getbike.helper.ExceptionHandler;

/**
 * Created by sivanookala on 25/10/16.
 */

public class BaseSyncher {

    public static ExceptionHandler exceptionHandler = new DefaultExceptionHandler();

    public static String BASE_URL = "http://192.168.10.18:9000";

    protected void handleException(Exception e) {
        exceptionHandler.handleException(e);
    }
}
