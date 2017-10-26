/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class DisplayDistance {
    private String distanceIndicatorUrl;
    private String distanceToTravel;

    public String getDistanceIndicatorUrl() {
        return distanceIndicatorUrl;
    }

    public void setDistanceIndicatorUrl(String distanceIndicatorUrl) {
        this.distanceIndicatorUrl = distanceIndicatorUrl;
    }

    public String getDistanceToTravel() {
        return distanceToTravel;
    }

    public void setDistanceToTravel(String distanceToTravel) {
        this.distanceToTravel = distanceToTravel;
    }
}
