package com.vave.getbike.syncher;

import com.vave.getbike.android.AndroidStubsFactory;
import com.vave.getbike.datasource.RideLocationDataSource;
import com.vave.getbike.model.Ride;
import com.vave.getbike.model.RideLocation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        cAssertRequestorDetails(actual);
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

    @Test
    public void openRidesTESTHappyFlow() {
        // Setup
        Ride ride1 = sut.requestRide(24.56, 24.57);
        Ride ride2 = sut.requestRide(24.56, 24.57);
        Ride ride3 = sut.requestRide(24.56, 24.57);
        // Exercise SUT
        List<Ride> actual = sut.openRides(24.56, 24.57);
        // Verify
        assertTrue(actual.size() > 3);
        assertEquals(ride3.getId(), actual.get(0).getId());
        assertEquals(ride2.getId(), actual.get(1).getId());
        assertEquals(ride1.getId(), actual.get(2).getId());
        cAssertRequestorDetails(actual.get(0));
        cAssertRequestorDetails(actual.get(1));
        cAssertRequestorDetails(actual.get(2));
    }

    @Test
    public void getMyCompletedRidesTESTHappyFlow() {
        // Setup
        Ride ride1 = sut.requestRide(24.56, 24.57);
        Ride ride2 = sut.requestRide(24.56, 24.57);
        Ride ride3 = sut.requestRide(24.56, 24.57);
        sut.acceptRide(ride2.getId());
        sut.closeRide(ride2.getId());
        sut.acceptRide(ride3.getId());
        sut.closeRide(ride3.getId());
        // Exercise SUT
        List<Ride> actual = sut.getMyCompletedRides();
        // Verify
        assertTrue(actual.size() > 1);
        assertEquals(ride3.getId(), actual.get(0).getId());
        assertEquals(ride2.getId(), actual.get(1).getId());
        cAssertRequestorDetails(actual.get(0));
        cAssertRequestorDetails(actual.get(1));
    }

    @Test
    public void getRidesGivenByMeTESTHappyFlow() {
        // Setup
        Ride ride1 = sut.requestRide(24.56, 24.57);
        Ride ride2 = sut.requestRide(24.56, 24.57);
        Ride ride3 = sut.requestRide(24.56, 24.57);
        sut.acceptRide(ride2.getId());
        sut.closeRide(ride2.getId());
        sut.acceptRide(ride3.getId());
        sut.closeRide(ride3.getId());
        // Exercise SUT
        List<Ride> actual = sut.getRidesGivenByMe();
        // Verify
        assertTrue(actual.size() > 1);
        assertEquals(ride3.getId(), actual.get(0).getId());
        assertEquals(ride2.getId(), actual.get(1).getId());
        cAssertRequestorDetails(actual.get(0));
        cAssertRequestorDetails(actual.get(1));
    }

    @Test
    public void getCompleteRideByIdTESTHappyFlow() {
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
        sut.closeRide(ride.getId());
        List<RideLocation> rideLocations = new ArrayList<>();
        // Exercise SUT
        Ride actual = sut.getCompleteRideById(ride.getId(), rideLocations);
        // Verify
        assertNotNull(actual);
        assertTrue(actual.getOrderDistance() > 0);
        int knownNumberOfRideLocations = 2;
        assertEquals(knownNumberOfRideLocations, rideLocations.size());
    }

    @Test
    public void estimateRideTESTHappyFlow() {
        // Setup
        ArrayList<RideLocation> rideLocations = new ArrayList<>();
        rideLocations.add(createRideLocation(23.45, 65.45));
        rideLocations.add(createRideLocation(23.46, 65.55));
        rideLocations.add(createRideLocation(23.47, 65.65));
        rideLocations.add(createRideLocation(23.48, 65.75));
        // Exercise SUT
        Ride actual = sut.estimateRide(rideLocations);
        // Verify
        assertEquals(30779.65820996688, actual.getOrderDistance());
        assertEquals(92.33897462990065, actual.getOrderAmount());
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

    private void cAssertRequestorDetails(Ride actual) {
        assertNotNull(actual.getRequestorAddress());
        assertNotNull(actual.getRequestorPhoneNumber());
        assertNotNull(actual.getRequestorName());
        assertNotNull(actual.getRequestedAt());
    }

    private RideLocation createRideLocation(double latitude, double longitude) {
        RideLocation result = new RideLocation();
        result.setLatitude(latitude);
        result.setLongitude(longitude);
        return result;
    }

}
