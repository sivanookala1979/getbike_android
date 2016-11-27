package com.vave.getbike.activity.suite;

import com.vave.getbike.activity.AcceptRejectRideActivityTest;
import com.vave.getbike.activity.MyCompletedRidesActivityTest;
import com.vave.getbike.activity.OpenRidesActivityTest;
import com.vave.getbike.activity.ShowCompletedRideActivityTest;
import com.vave.getbike.activity.WaitForRiderActivityTest;

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
        ShowCompletedRideActivityTest.class,
        WaitForRiderActivityTest.class
})
public class SecondaryTestsSuite {

}
