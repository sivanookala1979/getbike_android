package com.vave.getbike.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.vave.getbike.R;
import com.vave.getbike.syncher.BaseSyncher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
                .perform(typeText(generateRandomPhoneNumber()), closeSoftKeyboard());

        onView(withId(R.id.male))
                .perform(click());
        onView(withId(R.id.signup)).perform(click());
        // Check that the text was changed.
        // TODO: 01/11/16 Validate that the popup shows the message 'User already exists'
    }

    @Test
    public void signupTESTWithExistingUser() {
        onView(withId(R.id.name))
                .perform(typeText("Siva Nookala"), closeSoftKeyboard());
        onView(withId(R.id.email))
                .perform(typeText("siva.nookala@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.mobile))
                .perform(typeText("9949287789"), closeSoftKeyboard());

        onView(withId(R.id.male))
                .perform(click());
        onView(withId(R.id.signup)).perform(click());
        // TODO: 01/11/16 Validate that the popup shows the message 'User already exists'
    }

    public static String generateRandomPhoneNumber() {
        int size = 10;
        StringBuilder generatedToken = new StringBuilder();
        for (int i = 0; i < size; i++) {
            generatedToken.append(((int) (Math.random() * 10)) % 10);
        }
        return generatedToken.toString();
    }

}
