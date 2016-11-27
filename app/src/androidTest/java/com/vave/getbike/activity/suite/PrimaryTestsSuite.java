package com.vave.getbike.activity.suite;

import com.vave.getbike.activity.LoginActivity;
import com.vave.getbike.activity.LoginActivityTest;
import com.vave.getbike.activity.SignupActivity2Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by sivanookala on 27/11/16.
 */

// Runs all unit tests.
@RunWith(Suite.class)
@Suite.SuiteClasses({SignupActivity2Test.class,
        LoginActivityTest.class})
public class PrimaryTestsSuite {
}
