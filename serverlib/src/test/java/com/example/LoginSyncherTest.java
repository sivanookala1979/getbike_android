package com.example;

import com.vave.getbike.syncher.LoginSyncher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sivanookala on 25/10/16.
 */

public class LoginSyncherTest {

    LoginSyncher sut;

    @Test
    public void signupTESTHappyFlow() {
        Integer actual = sut.signup("Siva", "9949287789", "siva.nookala@gmail.com", 'M');
        assertNotNull(actual);
        assertTrue(actual > 0);
    }

    @Before
    public void setUp() {
        sut = new LoginSyncher();
    }

    @After
    public void tearDown() {
        sut = null;
    }

}
