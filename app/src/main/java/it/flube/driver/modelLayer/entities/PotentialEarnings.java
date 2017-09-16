/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created on 9/4/2017
 * Project : Driver
 */

public class PotentialEarnings {

    public enum EarningsType {
        FIXED_FEE,
        HOURLY_FEE
    }

    private EarningsType earningsType;
    private Boolean plusTips;
    private Integer payRateInCents;

    public EarningsType getEarningsType() {
        return earningsType;
    }

    public void setEarningsType(EarningsType earningsType) {
        this.earningsType = earningsType;
    }

    public Boolean getPlusTips() {
        return plusTips;
    }

    public void setPlusTips(Boolean plusTips) {
        this.plusTips = plusTips;
    }

    public Integer getPayRateInCents() {
        return payRateInCents;
    }

    public void setPayRateInCents(Integer payRateInCents) {
        this.payRateInCents = payRateInCents;
    }
}
