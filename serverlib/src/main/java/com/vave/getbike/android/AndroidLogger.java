package com.vave.getbike.android;

import android.util.Log;

/**
 * Created by sivanookala on 25/11/16.
 */

public class AndroidLogger implements ILogger {

    // TODO: 25/11/16 Ramkoti - please add the other methods for various levels of logging.

    @Override
    public int d(String tag, String msg) {
        return Log.d(tag, msg);
    }
}
