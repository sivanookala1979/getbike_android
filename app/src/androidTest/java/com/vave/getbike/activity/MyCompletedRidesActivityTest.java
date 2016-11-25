package com.vave.getbike.activity;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.vave.getbike.R;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.BaseSyncher;
import com.vave.getbike.syncher.RideSyncher;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MyCompletedRidesActivityTest {

    @Rule
    public ActivityTestRule<MyCompletedRidesActivity> mActivityTestRule = new ActivityTestRule<>(MyCompletedRidesActivity.class);

    @Test
    public void testInitialLaunch() {
        BaseSyncher.testSetup();
        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        RideSyncher rideSyncher = new RideSyncher();
        Ride ride1 = rideSyncher.requestRide(24.56, 24.57);
        Ride ride2 = rideSyncher.requestRide(24.56, 24.57);
        Ride ride3 = rideSyncher.requestRide(24.56, 24.57);
        rideSyncher.acceptRide(ride2.getId());
        rideSyncher.closeRide(ride2.getId());
        rideSyncher.acceptRide(ride3.getId());
        rideSyncher.closeRide(ride3.getId());
        Intent intent = new Intent(targetContext, MyCompletedRidesActivity.class);
        mActivityTestRule.launchActivity(intent);
        SystemClock.sleep(6000);
        onData(anything()).inAdapterView(withId(R.id.myCompletedRides)).atPosition(0).perform(click());
    }

}
