package com.vave.getbike.activity;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.vave.getbike.R;
import com.vave.getbike.syncher.BaseSyncher;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProfileAndSettingsActivityTest {

    @Rule
    public ActivityTestRule<ProfileAndSettingsActivity> mActivityTestRule = new ActivityTestRule<>(ProfileAndSettingsActivity.class);

    @Test
    public void testUpdateProfile() {
        BaseSyncher.testSetup();
        onView(withId(R.id.personalDetails)).perform(click());
        onView(withId(R.id.profileUserName)).perform(typeText("Adarsh T"), closeSoftKeyboard());
        onView(withId(R.id.userEmail)).perform(typeText("Tadarsh401@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.userOccupation)).perform(typeText("Apps Developer"), closeSoftKeyboard());
        onView(withId(R.id.userCity)).perform(typeText("Kavali"), closeSoftKeyboard());
        onView(withId(R.id.userYearOfBirth)).perform(typeText("1992"), closeSoftKeyboard());
        onView(withId(R.id.userHomeLocation)).perform(typeText("Kavali"), closeSoftKeyboard());
        onView(withId(R.id.userOfficeLocation)).perform(typeText("Kavali"), closeSoftKeyboard());
        onView(withId(R.id.userMobileNumber)).perform(typeText("9949257729"), closeSoftKeyboard());
        onView(withId(R.id.update)).perform(click());
//        onView(withId(R.id.personalDetails)).check(matches(isDisplayed()));
        SystemClock.sleep(40000);
    }
}
