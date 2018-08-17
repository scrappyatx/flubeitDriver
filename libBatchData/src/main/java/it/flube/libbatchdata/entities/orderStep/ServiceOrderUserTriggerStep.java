/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import it.flube.libbatchdata.entities.Timestamp;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderUserTriggerStep extends AbstractStep implements OrderStepInterface {

    private String displayMessage;
    private Boolean showElapsedTime;
    private long timerBase;

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public Boolean getShowElapsedTime() {
        return showElapsedTime;
    }

    public void setShowElapsedTime(Boolean showElapsedTime) {
        this.showElapsedTime = showElapsedTime;
    }

    public long getTimerBase() {
        return timerBase;
    }

    public void setTimerBase(long timerBase) {
        this.timerBase = timerBase;
    }
}
