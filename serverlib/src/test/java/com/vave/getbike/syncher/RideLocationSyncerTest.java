package com.vave.getbike.syncher;

import com.vave.getbike.android.AndroidStubsFactory;
import com.vave.getbike.datasource.RideLocationDataSource;
import com.vave.getbike.model.Ride;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by sivanookala on 01/11/16.
 */

public class RideLocationSyncerTest {

    RideLocationSyncher sut;

    @Test
    public void storePendingLocationsTESTHappyFlow() {
        RideSyncher rideSyncher = new RideSyncher();
        Ride ride = rideSyncher.requestRide(24.56, 24.57);
        sut.acceptRide(ride.getRideId());
        RideLocationDataSource dataSource = new RideLocationDataSource(null);
        dataSource.setUpdataSource();
        dataSource.clearAll();
        dataSource.insert(ride.getRideId(), new Date(), 21.98, 28.65, false);
        dataSource.insert(ride.getRideId(), new Date(), 21.986, 28.655, false);
        sut.setDataSource(dataSource);
        boolean actual = sut.storePendingLocations(ride.getRideId());
        assertTrue(actual);
        dataSource.close();
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
        sut = new RideLocationSyncher();
        BaseSyncher.testSetup();
        AndroidStubsFactory.IS_TEST = true;
    }

    @After
    public void tearDown() {
        sut = null;
    }

}
