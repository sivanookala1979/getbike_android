package com.vave.getbike.activity;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.vave.getbike.R;
import com.vave.getbike.syncher.BaseSyncher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.vave.getbike.utils.GetBikeTestUtils.isPositive;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SignupActivity2Test {

    @Rule
    public ActivityTestRule<SignupActivity> mActivityTestRule = new ActivityTestRule<>(SignupActivity.class);

    @Test
    public void signupTESTHappyFlow() {
        onView(withId(R.id.name))
                .perform(typeText("Siva Nookala"), closeSoftKeyboard());
        onView(withId(R.id.email))
                .perform(typeText("siva.nookala@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.mobile))
                .perform(typeText("9949287789"), closeSoftKeyboard());

        onView(withId(R.id.male))
                .perform(click());
        onView(withId(R.id.signup)).perform(click());
        // Check that the text was changed.
        onView(withId(R.id.resultUserId)).check(matches(isPositive()));
    }

    @Test
    public void loginTESTHappyFlow() {

        onView(withId(R.id.login)).perform(click());
        onView(withId(R.id.requestRide)).perform(click());
        onView(withId(R.id.getBikeResult)).check(matches(isPositive()));
    }


    @Test
    public void locationTESTHappyFlow() {

        onView(withId(R.id.location)).perform(click());
        onView(withId(R.id.start_updates_button)).perform(click());
        SystemClock.sleep(20000);
        onView(withId(R.id.start_updates_button)).perform(click());
    }
    @Before
    public void setUp() {
        BaseSyncher.testSetup();
    }

}
