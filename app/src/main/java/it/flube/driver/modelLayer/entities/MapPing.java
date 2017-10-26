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
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;
    private String mapPingHistoryGuid;

    private LatLonLocation latLonLocation;
    private Date pingTime;



    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getBatchGuid() {
        return batchGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public String getBatchDetailGuid() {
        return batchDetailGuid;
    }

    public void setBatchDetailGuid(String batchDetailGuid) {
        this.batchDetailGuid = batchDetailGuid;
    }

    public String getServiceOrderGuid() {
        return serviceOrderGuid;
    }

    public void setServiceOrderGuid(String serviceOrderGuid) {
        this.serviceOrderGuid = serviceOrderGuid;
    }

    public String getMapPingHistoryGuid() {
        return mapPingHistoryGuid;
    }

    public void setMapPingHistoryGuid(String mapPingHistoryGuid) {
        this.mapPingHistoryGuid = mapPingHistoryGuid;
    }

    public LatLonLocation getLatLonLocation() {
        return latLonLocation;
    }

    public void setLatLonLocation(LatLonLocation latLonLocation){
        this.latLonLocation = latLonLocation;
    }

    public Date getPingTime() {
        return pingTime;
    }

    public void setPingTime(Date pingTime) {
        this.pingTime = pingTime;
    }

}
