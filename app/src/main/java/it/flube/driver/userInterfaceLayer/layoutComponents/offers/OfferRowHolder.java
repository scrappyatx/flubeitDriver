/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents.offers;

import it.flube.libbatchdata.entities.batch.Batch;

/**
 * Created on 9/7/2018
 * Project : Driver
 */
public class OfferRowHolder {
    private Batch batch;
    private Long startTime;
    private String displayHeader;
    private Boolean showHeader;

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getDisplayHeader() {
        return displayHeader;
    }

    public void setDisplayHeader(String displayHeader) {
        this.displayHeader = displayHeader;
    }

    public Boolean getShowHeader() {
        return showHeader;
    }

    public void setShowHeader(Boolean showHeader) {
        this.showHeader = showHeader;
    }
}
