package com.vave.getbike;

import com.vave.getbike.syncher.BaseSyncher;
import com.vave.getbike.syncher.RideSyncher;

/**
 * Created by sivanookala on 30/11/16.
 */

public class ManualTriggers {

    public static void main(String s[]) {
        acceptRide(39);
    }

    public static void acceptRide(long rideId) {
        BaseSyncher.testSetup();
        boolean actual = new RideSyncher().acceptRide(47);
    }

}
