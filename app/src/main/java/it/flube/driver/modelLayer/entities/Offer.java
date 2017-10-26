/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import java.util.ArrayList;

/**
 * Created by Bryan on 3/21/2017.
 */

public class Offer {

    public enum OfferType {
        PRODUCTION,
        PRODUCTION_TEST,
        MOBILE_DEMO
    }

    private String guid;
    private OfferType offerType;
    private ServiceProvider serviceProvider;

    private String offerDate;
    private String offerTime;
    private String offerDuration;

    private ArrayList<RouteStop> routeList;

    private String serviceDescription;

    private String estimatedEarnings;
    private String estimatedEarningsExtra;

    public String getGUID() {
        return guid;
    }

    public void setGUID(String guid) {
        this.guid = guid;
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
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

    public ArrayList<RouteStop> getRouteList() {
        return routeList;
    }

    public void setRouteList(ArrayList<RouteStop> routeList) {
        this.routeList = routeList;
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
