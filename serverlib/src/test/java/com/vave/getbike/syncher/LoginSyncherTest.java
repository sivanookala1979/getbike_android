package com.vave.getbike.syncher;

import com.vave.getbike.datasource.CallStatus;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sivanookala on 25/10/16.
 */

public class LoginSyncherTest {

    LoginSyncher sut;

    @Test
    public void signupTESTHappyFlow() {
        CallStatus actual = sut.signup("Siva", UUID.randomUUID().toString(), "siva.nookala@gmail.com", 'M');
        assertNotNull(actual);
        assertTrue(actual.getId() > 0);
        assertTrue(actual.isSuccess());
    }

    @Test
    public void signupTESTWithDuplicateMobileNumber() {
        String randomMobileNumber = UUID.randomUUID().toString();
        sut.signup("Siva", randomMobileNumber, "siva.nookala@gmail.com", 'M');
        CallStatus actual = sut.signup("Siva", randomMobileNumber, "siva.nookala@gmail.com", 'M');
        assertEquals(9901, actual.getErrorCode());
        assertFalse(actual.isSuccess());
    }

    @Test
    public void loginTESTHappyFlow() {
        sut.signup("Siva", "9949287789", "siva.nookala@gmail.com", 'M');
        boolean actual = sut.login("9949287789");
        assertTrue(actual);
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
