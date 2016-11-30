package com.vave.getbike.syncher;

import com.vave.getbike.datasource.CallStatus;
import com.vave.getbike.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
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

    @Test
    public void storeGcmCodeTESTHappyFlow() {
        BaseSyncher.testSetup();
        String gcmCode = "cdcszGene8c:APA91bHQYHdw6Y1rM0JfWrtb_P36-OtE9_wYQb2hDxfPhZhDLYM9DKZipd2fT6QQnV1BUnkJUTZqbuuvotukeixEiMblhLCjQhVgeg9O91PrMxkBYJrnsCdJe3NpeAHGFGpzKbhuSvWz";
        boolean actual = sut.storeGcmCode(gcmCode);
        assertTrue(actual);
    }

    @Test
    public void getPublicProfileTESTHappyFlow() {
        BaseSyncher.testSetup();
        User actual = sut.getPublicProfile(3L);
        assertEquals("Siva", actual.getName());
        assertEquals("9949287789", actual.getPhoneNumber());
    }

    @Test
    public void storeVehicleNumberTESTHappyFlow() {
        BaseSyncher.testSetup();
        boolean actual = sut.storeVehicleNumber("aGVsbG8gaGVsbG8gaGVsbG8=", "AAp09Bf3497");
        assertTrue(actual);
    }

    @SuppressWarnings("Since15")
    @Test
    public void storeDrivingLicenseTESTHappyFlow() throws IOException {
        BaseSyncher.testSetup();
        InputStream resourceAsStream = new FileInputStream("src/test/java/com/vave/getbike/syncher/vehicle-number-plate.png");
        byte[] targetArray = new byte[resourceAsStream.available()];
        resourceAsStream.read(targetArray);
        byte[] encodedData = Base64.getEncoder().encode(targetArray);
        boolean actual = sut.storeDrivingLicense(new String(encodedData), "77362862");
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
