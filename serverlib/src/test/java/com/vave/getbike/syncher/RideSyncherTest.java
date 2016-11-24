package com.vave.getbike.syncher;

import com.vave.getbike.android.AndroidStubsFactory;
import com.vave.getbike.datasource.RideLocationDataSource;
import com.vave.getbike.model.Ride;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sivanookala on 26/10/16.
 */

public class RideSyncherTest {

    RideSyncher sut;

    @Test
    public void requestRideTESTHappyFlow() {
        Ride ride = sut.requestRide(54.2122, 98.22111);
        assertNotNull(ride);
        assertTrue(ride.getId() > 0);
    }

    @Test
    public void getRideByIdTESTHappyFlow() {
        Ride ride = sut.requestRide(24.56, 24.57);
        Ride actual = sut.getRideById(ride.getId());
        assertNotNull(actual);
        assertEquals(ride.getId(), actual.getId());
        assertNotNull(actual.getRequestorAddress());
        assertNotNull(actual.getRequestorPhoneNumber());
        assertNotNull(actual.getRequestorName());
        assertEquals(24.56, actual.getStartLatitude());
        assertEquals(24.57, actual.getStartLongitude());
    }

    @Test
    public void acceptRideTESTHappyFlow() {
        Ride ride = sut.requestRide(24.56, 24.57);
        boolean actual = sut.acceptRide(ride.getId());
        assertTrue(actual);
    }

    @Test
    public void closeRideTESTHappyFlow() {
        // Setup
        Ride ride = sut.requestRide(24.56, 24.57);
        sut.acceptRide(ride.getId());
        RideLocationDataSource dataSource = new RideLocationDataSource(null);
        dataSource.setUpdataSource();
        dataSource.clearAll();
        dataSource.insert(ride.getId(), new Date(), 21.98, 28.65, false);
        dataSource.insert(ride.getId(), new Date(), 21.986, 28.655, false);
        RideLocationSyncher locationSyncher = new RideLocationSyncher();
        locationSyncher.setDataSource(dataSource);
        locationSyncher.storePendingLocations(ride.getId());
        // Exercise SUT
        Ride actual = sut.closeRide(ride.getId());
        // Verify
        assertNotNull(actual);
        assertTrue(actual.getOrderDistance() > 0);
    }

    @Before
    public void setUp() {
        sut = new RideSyncher();
        BaseSyncher.testSetup();
        AndroidStubsFactory.IS_TEST = true;
    }

    @After
    public void tearDown() {
        sut = null;
    }
}
