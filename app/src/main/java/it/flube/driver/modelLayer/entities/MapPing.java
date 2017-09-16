/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import java.util.Date;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class MapPing {
    private String guid;
    private LatLonLocation latLonLocation;
    private Date timestamp;

    public String getGUID(){
        return guid;
    }

    public void setGUID(String guid){
        this.guid = guid;
    }

    public LatLonLocation getLatLonLocation() {
        return latLonLocation;
    }

    public void setLatLonLocation(LatLonLocation latLonLocation){
        this.latLonLocation = latLonLocation;
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }
}
