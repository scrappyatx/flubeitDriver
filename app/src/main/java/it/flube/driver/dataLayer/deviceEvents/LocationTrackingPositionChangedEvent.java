/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.deviceEvents;

import it.flube.libbatchdata.entities.LatLonLocation;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class LocationTrackingPositionChangedEvent {
    private LatLonLocation position;

    public LocationTrackingPositionChangedEvent(LatLonLocation position){
        this.position = position;
    }
    public LatLonLocation getPosition(){ return position; }
}
