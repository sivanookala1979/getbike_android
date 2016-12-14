package com.vave.getbike.activity;

import android.os.SystemClock;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.vave.getbike.R;
import com.vave.getbike.syncher.BaseSyncher;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class GiveRideTakeRideActivityTest {

    @Rule
    public ActivityTestRule<GiveRideTakeRideActivity> mActivityTestRule = new ActivityTestRule<>(GiveRideTakeRideActivity.class);

    @Test
    public void testKavaliToMusunur() {
        BaseSyncher.testSetup();
        onView(withId(R.id.takeRide)).perform(click());
        onView(withId(R.id.yourLocation)).check(matches(withText("Pullareddy Nagar Main Road, Rama Murthy Peta, Kavali, Andhra Pradesh 524201")));
        onView(withId(R.id.destination)).perform(typeText("Musunur"), ViewActions.closeSoftKeyboard());
        GiveRideTakeRideActivity mActivity = mActivityTestRule.getActivity();

        onView(withText("Musunuru, Andhra Pradesh, India"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));
        onView(withText("Musunur, Andhra Pradesh, India"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        // Tap on a suggestion.
        onView(withText("Musunur, Andhra Pradesh, India"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());

        // By clicking on the auto complete term, the text should be filled in.
        onView(withId(R.id.destination))
                .check(matches(withText("Musunur, Andhra Pradesh, India")));
        SystemClock.sleep(2000);
        onView(withId(R.id.rideEstimate))
                .check(matches(withText("Estimated ₹ 25.3 for 3.19 km")));
    }

    @Test
    public void testKavaliToHyderabad() {
        BaseSyncher.testSetup();
        onView(withId(R.id.takeRide)).perform(click());
        onView(withId(R.id.yourLocation)).check(matches(withText("Pullareddy Nagar Main Road, Rama Murthy Peta, Kavali, Andhra Pradesh 524201")));
        onView(withId(R.id.destination)).perform(typeText("Hyderabad"), ViewActions.closeSoftKeyboard());
        GiveRideTakeRideActivity mActivity = mActivityTestRule.getActivity();

        onView(withText("Hyderabad, Telangana, India"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        // Tap on a suggestion.
        onView(withText("Hyderabad, Telangana, India"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());

        // By clicking on the auto complete term, the text should be filled in.
        onView(withId(R.id.destination))
                .check(matches(withText("Hyderabad, Telangana, India")));
        SystemClock.sleep(2000);
        onView(withId(R.id.rideEstimate))
                .check(matches(withText("Estimated ₹ 2515.8 for 358.97 km")));
    }

    @Test
    public void giveRideToKnownUser() {
        BaseSyncher.testSetup();
        onView(withId(R.id.giveRide)).perform(click());
        onView(withId(R.id.yourLocation)).check(matches(withText("Pullareddy Nagar Main Road, Rama Murthy Peta, Kavali, Andhra Pradesh 524201")));
        onView(withId(R.id.customerMobileNumber)).perform(typeText("9949257729"));
        onView(withId(R.id.destination)).perform(typeText("Hyderabad"), ViewActions.closeSoftKeyboard());
        GiveRideTakeRideActivity mActivity = mActivityTestRule.getActivity();

        onView(withText("Hyderabad, Telangana, India"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        // Tap on a suggestion.
        onView(withText("Hyderabad, Telangana, India"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .perform(click());

        // By clicking on the auto complete term, the text should be filled in.
        onView(withId(R.id.destination))
                .check(matches(withText("Hyderabad, Telangana, India")));
        SystemClock.sleep(2000);
        onView(withId(R.id.rideEstimate))
                .check(matches(withText("Estimated ₹ 2515.8 for 358.97 km")));
        onView(withId(R.id.giveRide)).perform(click());
        AtomicReference<String> rideIdCapture = new AtomicReference<>();
        onView(withId(R.id.tripId)).check(matches(LoginActivityTest.textCapture(rideIdCapture)));
        onView(withId(R.id.start_updates_button)).perform(click());
        SystemClock.sleep(4000);
        onView(withId(R.id.stop_updates_button)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.tripId)).check(matches(withText(rideIdCapture.get())));
    }

}
