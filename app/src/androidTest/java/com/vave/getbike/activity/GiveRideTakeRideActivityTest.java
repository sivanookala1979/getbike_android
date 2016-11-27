package com.vave.getbike.activity;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.vave.getbike.R;
import com.vave.getbike.syncher.BaseSyncher;
import com.vave.getbike.syncher.RideSyncher;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class GiveRideTakeRideActivityTest {

    @Rule
    public ActivityTestRule<GiveRideTakeRideActivity> mActivityTestRule = new ActivityTestRule<>(GiveRideTakeRideActivity.class);

    @Test
    public void testInitialLaunch() {
        onView(withId(R.id.takeRide)).perform(click());
        onView(withId(R.id.yourLocation)).check(matches(withText("Dall Mill Street, Rama Murthy Peta, Kavali, Andhra Pradesh 524201")));
        SystemClock.sleep(5000);
    }
}
