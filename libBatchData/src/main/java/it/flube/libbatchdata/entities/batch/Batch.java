/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.batch;

import java.util.ArrayList;
import java.util.Date;

import it.flube.libbatchdata.entities.DisplayDistance;
import it.flube.libbatchdata.entities.DisplayTiming;
import it.flube.libbatchdata.entities.PotentialEarnings;


/**
 * Created on 3/22/2017
 * Package : ${PACKAGE_NAME}
 * Project : Driver
 */

public class Batch {

    private String guid;

    private String title;
    private String iconUrl;


    private DisplayDistance displayDistance;
    private PotentialEarnings potentialEarnings;

    private Long expectedStartTime;
    private Long expectedFinishTime;
    private Long offerExpiryTime;

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconURL) {
        this.iconUrl = iconURL;
    }

    public DisplayDistance getDisplayDistance() {
        return displayDistance;
    }

    public void setDisplayDistance(DisplayDistance displayDistance) {
        this.displayDistance = displayDistance;
    }

    public PotentialEarnings getPotentialEarnings() {
        return potentialEarnings;
    }

    public void setPotentialEarnings(PotentialEarnings potentialEarnings) {
        this.potentialEarnings = potentialEarnings;
    }

    public Long getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(Long expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public Long getExpectedFinishTime() {
        return expectedFinishTime;
    }

    public void setExpectedFinishTime(Long expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }

    public Long getOfferExpiryTime() {
        return offerExpiryTime;
    }

    public void setOfferExpiryTime(Long offerExpiryTime) {
        this.offerExpiryTime = offerExpiryTime;
    }
}
