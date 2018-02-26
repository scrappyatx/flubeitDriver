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

    private DisplayTiming displayTiming;
    private DisplayDistance displayDistance;
    private PotentialEarnings potentialEarnings;

    private Date expectedStartTime;
    private Date expectedFinishTime;


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

    public void setDisplayTiming(DisplayTiming displayTiming) {
        this.displayTiming = displayTiming;
    }

    public DisplayDistance getDisplayDistance() {
        return displayDistance;
    }

    public void setDisplayDistance(DisplayDistance displayDistance) {
        this.displayDistance = displayDistance;
    }

    public DisplayTiming getDisplayTiming() {
        return displayTiming;
    }


    public PotentialEarnings getPotentialEarnings() {
        return potentialEarnings;
    }

    public void setPotentialEarnings(PotentialEarnings potentialEarnings) {
        this.potentialEarnings = potentialEarnings;
    }

    public Date getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(Date expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public Date getExpectedFinishTime() {
        return expectedFinishTime;
    }

    public void setExpectedFinishTime(Date expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }
}
