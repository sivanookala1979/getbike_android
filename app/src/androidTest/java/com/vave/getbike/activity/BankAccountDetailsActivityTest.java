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
 * Created by sivanookala on 01/11/16.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class BankAccountDetailsActivityTest {

    @Rule
    public ActivityTestRule<BankAccountDetailsActivity> mActivityTestRule = new ActivityTestRule<>(BankAccountDetailsActivity.class);

    @Test
    public void testUpdateBankAccountDetailsHappyFlow() {
        BaseSyncher.testSetup();
        onView(withId(R.id.accountHolderName)).perform(typeText("Adarsh T"), closeSoftKeyboard());
        onView(withId(R.id.accountNumber)).perform(typeText("12345678"), closeSoftKeyboard());
        onView(withId(R.id.ifscCode)).perform(typeText("IFC120003"), closeSoftKeyboard());
        onView(withId(R.id.bankName)).perform(typeText("Axis"), closeSoftKeyboard());
        onView(withId(R.id.branchName)).perform(typeText("KAVALI BRANCH"), closeSoftKeyboard());
        onView(withId(R.id.updateBankDetails)).perform(click());
        SystemClock.sleep(5000);
    }
    @Test
    public void testUpdateBankAccountDetailsWithEmptyAccountHolderName() {
        BaseSyncher.testSetup();
        onView(withId(R.id.accountNumber)).perform(typeText("12345678"), closeSoftKeyboard());
        onView(withId(R.id.ifscCode)).perform(typeText("IFC120003"), closeSoftKeyboard());
        onView(withId(R.id.bankName)).perform(typeText("Axis"), closeSoftKeyboard());
        onView(withId(R.id.branchName)).perform(typeText("KAVALI BRANCH"), closeSoftKeyboard());
        onView(withId(R.id.updateBankDetails)).perform(click());
        SystemClock.sleep(5000);
    }
    @Test
    public void testUpdateBankAccountDetailsWithEmptyBankName() {
        BaseSyncher.testSetup();
        onView(withId(R.id.accountHolderName)).perform(typeText("Adarsh T"), closeSoftKeyboard());
        onView(withId(R.id.accountNumber)).perform(typeText("12345678"), closeSoftKeyboard());
        onView(withId(R.id.ifscCode)).perform(typeText("IFC120003"), closeSoftKeyboard());
        onView(withId(R.id.branchName)).perform(typeText("KAVALI BRANCH"), closeSoftKeyboard());
        onView(withId(R.id.updateBankDetails)).perform(click());
        SystemClock.sleep(5000);
    }
}
