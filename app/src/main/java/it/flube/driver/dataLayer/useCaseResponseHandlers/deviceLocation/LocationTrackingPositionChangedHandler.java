/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.deviceLocation;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.deviceEvents.LocationTrackingPositionChangedEvent;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import timber.log.Timber;

/**
 * Created on 7/28/2017
 * Project : Driver
 */

public class LocationTrackingPositionChangedHandler implements LocationTelemetryInterface.LocationTrackingPositionChanged {
    private final static String TAG = "LocationTrackingPositionChangedHandler";

    public void positionChanged(LatLonLocation position) {
            Timber.tag(TAG).d("position changed latitude -> " + position.getLatitude() + " longitude -> " + position.getLongitude());
            EventBus.getDefault().post(new LocationTrackingPositionChangedEvent(position));
    }

}
