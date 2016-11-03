package com.vave.getbike.activity;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.vave.getbike.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.vave.getbike.utils.GetBikeTestUtils.isPositive;

/**
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LocationActivityTest {
    @Rule
    public ActivityTestRule<SignupActivity> mActivityTestRule = new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void locationTESTHappyFlow() {
        onView(withId(R.id.requestRide)).perform(click());
        onView(withId(R.id.requestRide)).perform(click());
        onView(withId(R.id.getBikeResult)).check(matches(isPositive()));

        onView(withId(R.id.startRide)).perform(click());
        onView(withId(R.id.start_updates_button)).perform(click());
        SystemClock.sleep(20000);
        onView(withId(R.id.stop_updates_button)).perform(click());
        SystemClock.sleep(20000);
    }


}