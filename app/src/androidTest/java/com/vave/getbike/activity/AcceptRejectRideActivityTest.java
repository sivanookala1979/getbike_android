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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

/**
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class AcceptRejectRideActivityTest {

    @Rule
    public ActivityTestRule<AcceptRejectRideActivity> mActivityTestRule = new ActivityTestRule<>(AcceptRejectRideActivity.class);

    @Test
    public void withInvalidRideId() {

        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, AcceptRejectRideActivity.class);
        intent.putExtra("rideId", "200");

        mActivityTestRule.launchActivity(intent);
        onView(withText(R.string.error_ride_is_not_valid)).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void withValidRide() {
        BaseSyncher.testSetup();
        RideSyncher rideSyncher = new RideSyncher();
        Ride ride = rideSyncher.requestRide(21.34, 54.67);

        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, AcceptRejectRideActivity.class);
        intent.putExtra("rideId", ride.getId());

        mActivityTestRule.launchActivity(intent);
        SystemClock.sleep(3000);
        onView(withId(R.id.rideRequestLatLng)).check(matches(withText("21.34,54.67")));
        onView(withId(R.id.rideRequestedBy)).check(matches(withText("Siva Nookala ")));
        onView(withId(R.id.rideRequestMobileNumber)).check(matches(withText("9949287789")));
        onView(withId(R.id.rideRequestAddress)).check(matches(withText("Address of 21.34,54.67")));
    }

    @Test
    public void clickOnAcceptRide() {
        BaseSyncher.testSetup();
        RideSyncher rideSyncher = new RideSyncher();
        Ride ride = rideSyncher.requestRide(21.34, 54.67);

        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, AcceptRejectRideActivity.class);
        intent.putExtra("rideId", ride.getId());

        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.rideRequestLatLng)).check(matches(withText("21.34,54.67")));
        onView(withId(R.id.rideRequestedBy)).check(matches(withText("Siva Nookala ")));
        onView(withId(R.id.rideRequestMobileNumber)).check(matches(withText("9949287789")));
        onView(withId(R.id.rideRequestAddress)).check(matches(withText("Address of 21.34,54.67")));
        onView(withId(R.id.acceptRide)).perform(click());
        onView(withId(R.id.start_updates_button)).check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRejectRide() {
        BaseSyncher.testSetup();
        RideSyncher rideSyncher = new RideSyncher();
        Ride ride = rideSyncher.requestRide(21.34, 54.67);

        Context targetContext = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intent = new Intent(targetContext, AcceptRejectRideActivity.class);
        intent.putExtra("rideId", ride.getId());

        mActivityTestRule.launchActivity(intent);
        onView(withId(R.id.rideRequestLatLng)).check(matches(withText("21.34,54.67")));
        onView(withId(R.id.rideRequestedBy)).check(matches(withText("Siva Nookala ")));
        onView(withId(R.id.rideRequestMobileNumber)).check(matches(withText("9949287789")));
        onView(withId(R.id.rideRequestAddress)).check(matches(withText("Address of 21.34,54.67")));
        onView(withId(R.id.rejectRide)).perform(click());
        assertTrue(mActivityTestRule.getActivity().isFinishing());
    }

}
