/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bryan on 3/21/2017.
 */

public class Offer {
    private String offerOID;
    private ServiceProvider mServiceProvider;
    private String serviceProviderImage;
    private String offerDate;
    private String offerTime;
    private String offerDuration;
    private Location pickupLocation;
    private Location serviceLocation;
    private Location returnLocation;
    private String serviceDescription;
    private String estimatedEarnings;
    private String estimatedEarningsExtra;

    public String getOfferOID() {
        return offerOID;
    }

    public void setOfferOID(String offerOID) {
        this.offerOID = offerOID;
    }

    public ServiceProvider getServiceProvider() {
        return mServiceProvider;
    }

    public void setServiceProvider(ServiceProvider mServiceProvider) {
        this.mServiceProvider = mServiceProvider;
    }

    public String getServiceProviderImage() {
        return serviceProviderImage;
    }

    public void setServiceProviderImage(String serviceProviderImage) {
        this.serviceProviderImage = serviceProviderImage;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public String getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(String offerTime) {
        this.offerTime = offerTime;
    }

    public String getOfferDuration() {
        return offerDuration;
    }

    public void setOfferDuration(String offerDuration) {
        this.offerDuration = offerDuration;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Location getServiceLocation() {
        return serviceLocation;
    }

    public void setServiceLocation(Location serviceLocation) {
        this.serviceLocation = serviceLocation;
    }

    public Location getReturnLocation() {
        return returnLocation;
    }

    public void setReturnLocation(Location returnLocation) {
        this.returnLocation = returnLocation;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getEstimatedEarnings() {
        return estimatedEarnings;
    }

    public void setEstimatedEarnings(String estimatedEarnings) {
        this.estimatedEarnings = estimatedEarnings;
    }

    public String getEstimatedEarningsExtra() {
        return estimatedEarningsExtra;
    }

    public void setEstimatedEarningsExtra(String estimatedEarningsExtra) {
        this.estimatedEarningsExtra = estimatedEarningsExtra;
    }


    public Offer() {
    }

}
