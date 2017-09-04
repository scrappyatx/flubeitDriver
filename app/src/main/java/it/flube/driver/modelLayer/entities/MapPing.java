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
    private LatLonPosition latLonPosition;
    private Date timestamp;

    public String getGUID(){
        return guid;
    }

    public void setGUID(String guid){
        this.guid = guid;
    }

    public LatLonPosition getLatLonPosition() {
        return latLonPosition;
    }

    public void setLatLonPosition(LatLonPosition latLonPosition){
        this.latLonPosition = latLonPosition;
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }
}
