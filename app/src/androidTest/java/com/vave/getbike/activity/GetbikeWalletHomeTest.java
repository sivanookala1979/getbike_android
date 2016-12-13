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
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class GetBikeWalletHomeTest {

    @Rule
    public ActivityTestRule<GetBikeWalletHome> mActivityTestRule = new ActivityTestRule<>(GetBikeWalletHome.class);

    @Test
    public void tesShowHideWalletDetails() {
        BaseSyncher.testSetup();
        onView(withId(R.id.redeem)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.rechargeMobile)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.rechargeMobile)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.redeemToWallet)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.redeemToWallet)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.redeemToBank)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.redeemToBank)).perform(click());
        SystemClock.sleep(1000);
//        onView(withId(R.id.personalDetails)).check(matches(isDisplayed()));
        SystemClock.sleep(40000);
    }
}
