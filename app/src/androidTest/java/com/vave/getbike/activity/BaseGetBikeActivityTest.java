package com.vave.getbike.activity;

import android.app.Activity;
import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.vave.getbike.syncher.LoginSyncher;
import com.vave.getbike.syncher.RideSyncher;

import java.util.Collection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by sivanookala on 13/12/16.
 */

public class BaseGetBikeActivityTest {

    protected Activity currentActivity;

    public static void closeCurrentRide() {
        Long previousRideId = new LoginSyncher().getCurrentRide();
        if (previousRideId != null) {
            new RideSyncher().closeRide(previousRideId);
        }
    }

    protected void assertToast(int errorId, ActivityTestRule mActivityTestRule) {
        onView(withText(errorId)).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        compulsoryWait(2000);
    }

    protected void assertToast(String errorMessage, ActivityTestRule mActivityTestRule) {
        onView(withText(errorMessage)).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        compulsoryWait(2000);
    }

    protected Activity getActivityInstance() {
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection<Activity> resumedActivity = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                for (Activity act : resumedActivity) {
                    currentActivity = act;
                    break;
                }

            }
        });

        return currentActivity;
    }

    public void manualReview(int milliSeconds) {
        SystemClock.sleep(milliSeconds);
    }

    public void compulsoryWait(int milliSeconds) {
        SystemClock.sleep(milliSeconds);
    }

    public void waitForever() {
        SystemClock.sleep(3828228);
    }
}
