package com.vave.getbike.syncher;

import com.vave.getbike.model.Ride;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        assertTrue(ride.getRideId() > 0);
    }

    @Test
    public void acceptRideTESTHappyFlow() {
        RideSyncher rideSyncher = new RideSyncher();
        Ride ride = rideSyncher.requestRide(24.56, 24.57);
        boolean actual = sut.acceptRide(ride.getRideId());
        assertTrue(actual);
    }

    @Before
    public void setUp() {
        sut = new RideSyncher();
        BaseSyncher.testSetup();
    }

    @After
    public void tearDown() {
        sut = null;
    }
}
