/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.googlePlayLocation;

import android.location.Location;
import android.support.annotation.NonNull;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.List;

import it.flube.driver.dataLayer.useCaseResponseHandlers.deviceLocation.LocationTrackingPositionChangedHandler;
import it.flube.libbatchdata.entities.LatLonLocation;
import timber.log.Timber;

/**
 * Created on 1/19/2018
 * Project : Driver
 */

public class LocationCallbackWrapper extends LocationCallback {

    private static final String TAG = "LocationCallbackWrapper";

    private LocationTrackingPositionChangedHandler update;
    private UpdateLastGoodPositionInterface lastGoodPosition;

    public LocationCallbackWrapper(UpdateLastGoodPositionInterface lastGoodPosition){
        update = new LocationTrackingPositionChangedHandler();
        this.lastGoodPosition = lastGoodPosition;
        Timber.tag(TAG).d("created");
    }

    @Override
    public void onLocationAvailability(LocationAvailability locationAvailability){
        Timber.tag(TAG).d("onLocationAvailability START...");
        super.onLocationAvailability(locationAvailability);
        Timber.tag(TAG).d("   ...locationAvailable = " + locationAvailability.isLocationAvailable());
        Timber.tag(TAG).d("...onLocationAvailability COMPLETE");
    }

    @Override
    public void onLocationResult(LocationResult locationResult){
        Timber.tag(TAG).d("onLocationResult START...");
        super.onLocationResult(locationResult);

        List<Location> locationList = locationResult.getLocations();

        Timber.tag(TAG).d("   ...got " + locationList.size() + " locations");

        for (Location location : locationList){

            LatLonLocation latLonLocation = new LatLonLocation();
            latLonLocation.setLatitude(location.getLatitude());
            latLonLocation.setLongitude(location.getLongitude());
            Timber.tag(TAG).d("      ...latitude -> " + latLonLocation.getLatitude() + " longitude -> " + latLonLocation.getLongitude());

            update.positionChanged(latLonLocation);
            lastGoodPosition.locationUpdated(location);
        }
        Timber.tag(TAG).d("...onLocationResult COMPLETE");
    }

    public interface UpdateLastGoodPositionInterface {
        void locationUpdated(@NonNull Location location);
    }

}
