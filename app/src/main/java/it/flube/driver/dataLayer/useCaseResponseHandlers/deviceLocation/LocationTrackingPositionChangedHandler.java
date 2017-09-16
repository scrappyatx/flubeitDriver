/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.deviceLocation;

import org.greenrobot.eventbus.EventBus;

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
            EventBus.getDefault().postSticky(new LocationTrackingPositionChangedEvent(position));
    }

    public static class LocationTrackingPositionChangedEvent {
        private LatLonLocation position;

        public LocationTrackingPositionChangedEvent(LatLonLocation position){
            this.position = position;
        }
        public LatLonLocation getPosition(){ return position; }
    }
}
