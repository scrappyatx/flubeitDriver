/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.LatLonLocation;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class RouteStop {
    private String guid;
    private String batchGuid;
    private String batchDetailGuid;

    private Integer sequence;
    private LatLonLocation latLonLocation;
    private AddressLocation addressLocation;
    private String description;
    private String iconUrl;


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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public LatLonLocation getLatLonLocation() {
        return latLonLocation;
    }

    public void setLatLonLocation(LatLonLocation latLonLocation) {
        this.latLonLocation = latLonLocation;
    }

    public AddressLocation getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(AddressLocation addressLocation) {
        this.addressLocation = addressLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
