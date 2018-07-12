/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.driver;

/**
 * Created on 7/9/2018
 * Project : Driver
 */
public class PhoneSettings {
    private String dialNumber;
    private String displayNumber;
    private Boolean canVoice;
    private Boolean canSMS;

    public String getDialNumber() {
        return dialNumber;
    }

    public void setDialNumber(String dialNumber) {
        this.dialNumber = dialNumber;
    }

    public String getDisplayNumber() {
        return displayNumber;
    }

    public void setDisplayNumber(String displayNumber) {
        this.displayNumber = displayNumber;
    }

    public Boolean getCanVoice() {
        return canVoice;
    }

    public void setCanVoice(Boolean canVoice) {
        this.canVoice = canVoice;
    }

    public Boolean getCanSMS() {
        return canSMS;
    }

    public void setCanSMS(Boolean canSMS) {
        this.canSMS = canSMS;
    }
}
