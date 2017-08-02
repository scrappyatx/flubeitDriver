/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.location.Location;

import it.flube.driver.modelLayer.entities.LatLonPosition;

/**
 * Created on 7/27/2017
 * Project : Driver
 */

public interface LocationTelemetryInterface {

    void locationTrackingStartRequest(LocationTrackingStartResponse response, LocationTrackingPositionChanged update );

    interface LocationTrackingStartResponse {

        void locationTrackingStartSuccess();

        void locationTrackingStartFailure();
    }

    interface LocationTrackingPositionChanged {

        void positionChanged(LatLonPosition position);

    }

    void locationTrackingStopRequest(LocationTrackingStopResponse response);

    interface LocationTrackingStopResponse {

        void locationTrackingStopComplete();

    }

}
