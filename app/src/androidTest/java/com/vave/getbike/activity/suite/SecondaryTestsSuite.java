package com.vave.getbike.activity.suite;

import com.vave.getbike.activity.AcceptRejectRideActivityTest;
import com.vave.getbike.activity.MyCompletedRidesActivityTest;
import com.vave.getbike.activity.OpenRidesActivityTest;
import com.vave.getbike.activity.RidesGivenByMeActivityTest;
import com.vave.getbike.activity.ShowCompletedRideActivityTest;
import com.vave.getbike.activity.WaitForRiderAllocationActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by sivanookala on 27/11/16.
 */

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AcceptRejectRideActivityTest.class,
        MyCompletedRidesActivityTest.class,
        OpenRidesActivityTest.class,
        RidesGivenByMeActivityTest.class,
        ShowCompletedRideActivityTest.class,
        WaitForRiderAllocationActivityTest.class
})
public class SecondaryTestsSuite {

}
