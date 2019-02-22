/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

/**
 * Created by Bryan on 3/21/2017.
 */

public class ServiceProvider {
    private String name;
    private String iconURL;
    private ContactPerson contactPerson;
    private AddressLocation addressLocation;
    private LatLonLocation latLonLocation;


    public ServiceProvider() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    public AddressLocation getAddressLocation() {
        return addressLocation;
    }

    public void setAddressLocation(AddressLocation addressLocation) {
        this.addressLocation = addressLocation;
    }

    public LatLonLocation getLatLonLocation() {
        return latLonLocation;
    }

    public void setLatLonLocation(LatLonLocation latLonLocation) {
        this.latLonLocation = latLonLocation;
    }


}
