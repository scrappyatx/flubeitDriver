/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class RoutingStop {

    private LatLonLocation latLonLocation;
    private AddressLocation addressLocation;
    private String description;
    private String iconURL;

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

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }
}
