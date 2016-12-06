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
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by adarsht on 06/12/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class RedeemAmountActivityTest {
    @Rule
    public ActivityTestRule<RedeemAmountActivity> mActivityTestRule = new ActivityTestRule<>(RedeemAmountActivity.class);

    @Test
    public void tesRechargeMobileHappyFlow() {
        BaseSyncher.testSetup();
        onView(withId(R.id.rechargeMobile)).perform(click());
        onView(withId(R.id.rechargeMobileNumber))
                .perform(typeText("9949257729"), closeSoftKeyboard());
        onView(withId(R.id.rechargeAmount))
                .perform(typeText("50"), closeSoftKeyboard());
        onView(withId(R.id.rechargeSubmit)).perform(click());
        SystemClock.sleep(5000);
//        onView(withId(R.id.personalDetails)).check(matches(isDisplayed()));
    }

    @Test
    public void tesRechargeMobileWithEmptyAmount() {
        BaseSyncher.testSetup();
        onView(withId(R.id.rechargeMobile)).perform(click());
        onView(withId(R.id.rechargeMobileNumber))
                .perform(typeText("9949257729"), closeSoftKeyboard());
        onView(withId(R.id.rechargeSubmit)).perform(click());
        SystemClock.sleep(5000);

    }

    @Test
    public void tesRedeemWalletHappyFlow() {
        BaseSyncher.testSetup();
        onView(withId(R.id.redeemToWallet)).perform(click());
        onView(withId(R.id.redeemMobileNumber))
                .perform(typeText("9949257729"), closeSoftKeyboard());
        onView(withId(R.id.redeemAmount))
                .perform(typeText("20.0"), closeSoftKeyboard());
        onView(withId(R.id.redeemSubmit)).perform(click());
        SystemClock.sleep(5000);
    }

    @Test
    public void tesRedeemWalletEmptyMobileNumber() {
        BaseSyncher.testSetup();
        onView(withId(R.id.redeemToWallet)).perform(click());
        onView(withId(R.id.redeemAmount))
                .perform(typeText("50.0"), closeSoftKeyboard());
        onView(withId(R.id.redeemSubmit)).perform(click());
        SystemClock.sleep(5000);

    }

    @Test
    public void tesRedeemBankHappyFlow() {
        BaseSyncher.testSetup();
        onView(withId(R.id.redeemToBank)).perform(click());
        onView(withId(R.id.bankAmount))
                .perform(typeText("20.0"), closeSoftKeyboard());
        onView(withId(R.id.redeemBankSubmit)).perform(click());
        SystemClock.sleep(5000);
    }

    @Test
    public void tesRedeemBankInvalidAmount() {
        BaseSyncher.testSetup();
        onView(withId(R.id.redeemToBank)).perform(click());
        onView(withId(R.id.redeemBankSubmit)).perform(click());
        SystemClock.sleep(5000);
    }
}
