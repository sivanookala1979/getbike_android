package com.vave.getbike.activity;

import android.os.SystemClock;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.TextView;

import com.vave.getbike.R;
import com.vave.getbike.model.Ride;
import com.vave.getbike.syncher.RideSyncher;
import com.vave.getbike.utils.SMSIdlingResource;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.vave.getbike.utils.GetBikeTestUtils.isPositive;

/**
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    public static Matcher<View> textCapture(final AtomicReference<String> ref) {
        return new BoundedMatcher<View, TextView>(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with text: ");
            }

            @Override
            public boolean matchesSafely(TextView textView) {
                ref.set(textView.getText().toString());
                return true;
            }
        };
    }

    public void loginTESTHappyFlow_OldTest() {
        SMSIdlingResource smsIdlingResource = new SMSIdlingResource(mActivityTestRule.getActivity());
        IdlingPolicies.setIdlingResourceTimeout(60, TimeUnit.SECONDS);
        Espresso.registerIdlingResources(smsIdlingResource);

        onView(withId(R.id.mobile))
                .perform(typeText("9949287789"), closeSoftKeyboard());
        onView(withId(R.id.send_otp)).perform(click());
        smsIdlingResource.waitForSms();
        onView(withId(R.id.received_otp)).check(matches(isPositive()));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.requestRide)).perform(click());
        onView(withId(R.id.getBikeResult)).check(matches(isPositive()));
        onView(withId(R.id.startRide)).perform(click());
        onView(withId(R.id.start_updates_button)).perform(click());
        SystemClock.sleep(20000);
        onView(withId(R.id.stop_updates_button)).perform(click());
        onView(withId(R.id.closeRide)).perform(click());
        onView(withId(R.id.totalDistance)).check(matches(isPositive()));
        SystemClock.sleep(10000);

    }

    @Test
    public void loginTESTHappyFlow() {
        SMSIdlingResource smsIdlingResource = new SMSIdlingResource(mActivityTestRule.getActivity());
        IdlingPolicies.setIdlingResourceTimeout(60, TimeUnit.SECONDS);
        Espresso.registerIdlingResources(smsIdlingResource);

        onView(withId(R.id.mobile))
                .perform(typeText("9949287789"), closeSoftKeyboard());
        onView(withId(R.id.send_otp)).perform(click());
        smsIdlingResource.waitForSms();
        onView(withId(R.id.received_otp)).check(matches(isPositive()));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.requestRide)).perform(click());
        onView(withId(R.id.generatedRideId)).check(matches(isPositive()));
        AtomicReference<String> rideIdCapture = new AtomicReference<>();
        onView(withId(R.id.generatedRideId)).check(matches(textCapture(rideIdCapture)));
        RideSyncher rideSyncher = new RideSyncher();
        Ride rideFromServer = rideSyncher.getRideById(Long.parseLong(rideIdCapture.get()));
        onView(withId(R.id.rideRequestedAt)).check(matches(withText(rideFromServer.getRequestedAt() + "")));
    }

    @Test
    public void loginTESTOpenRidesActivity() {
        SMSIdlingResource smsIdlingResource = new SMSIdlingResource(mActivityTestRule.getActivity());
        IdlingPolicies.setIdlingResourceTimeout(60, TimeUnit.SECONDS);
        Espresso.registerIdlingResources(smsIdlingResource);

        onView(withId(R.id.mobile))
                .perform(typeText("9949287789"), closeSoftKeyboard());
        onView(withId(R.id.send_otp)).perform(click());
        smsIdlingResource.waitForSms();
        onView(withId(R.id.received_otp)).check(matches(isPositive()));
        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.showOpenRides)).perform(click());
        onView(withId(R.id.openRides)).check(matches(isDisplayed()));
    }

}
