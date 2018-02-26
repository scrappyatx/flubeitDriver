/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.libbatchdata.entities.LatLonLocation;

/**
 * Created on 7/27/2017
 * Project : Driver
 */

public interface LocationTelemetryInterface {

    void locationTrackingStartRequest(LocationTrackingStartResponse response);

    interface LocationTrackingStartResponse {

        void locationTrackingStartSuccess();

        void locationTrackingStartFailure();
    }

    interface LocationTrackingPositionChanged {

        void positionChanged(LatLonLocation position);

    }

    void locationTrackingStopRequest(LocationTrackingStopResponse response);

    interface LocationTrackingStopResponse {

        void locationTrackingStopComplete();

    }

    Boolean hasLastGoodPosition();

    LatLonLocation getLastGoodPosition();

}
