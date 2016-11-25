package com.vave.getbike.android;

/**
 * Created by sivanookala on 25/11/16.
 */

public class CeroneLogger implements ILogger {

    @Override
    public int d(String tag, String msg) {
        System.out.println(tag + " " + msg);
        return 0;
    }
}
