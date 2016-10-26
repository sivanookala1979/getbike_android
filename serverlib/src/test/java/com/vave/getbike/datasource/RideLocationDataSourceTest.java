package com.vave.getbike.datasource;

import com.vave.getbike.model.RideLocation;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by sivanookala on 26/10/16.
 */

public class RideLocationDataSourceTest extends BaseDataSourceTest {

    @Test
    public void insertTESTHappyFlow() {
        RideLocationDataSource sut = new RideLocationDataSource(null);
        sut.setUpdataSource();
        sut.clearAll();
        long rideId = 234L;
        int countBefore = sut.getRideLocations(rideId).size();
        sut.insert(rideId, new Date(), 21.45, 82.54);
        List<RideLocation> rideLocations = sut.getRideLocations(rideId);
        assertEquals(countBefore + 1, rideLocations.size());
        RideLocation newRideLocation = rideLocations.get(countBefore);
        assertEquals(rideId, newRideLocation.getRideId().longValue());
    }

}
