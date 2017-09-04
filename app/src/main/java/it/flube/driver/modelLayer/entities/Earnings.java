/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created on 8/12/2017
 * Project : Driver
 */

public class Earnings {
    private String earningsDescription;
    private Long earningsCents;


    public void setEarningsDescription(String earningsDescription) {
        this.earningsDescription = earningsDescription;
    }

    public String getEarningsDescription(){
        return earningsDescription;
    }

    public void setEarningsCents(Long earningsCents){
        this.earningsCents = earningsCents;
    }

    public Long getEarningsCents(){
        return earningsCents;
    }

    public void setEarningsDollars(Double earningsDollars) {
       earningsCents = Math.round(100 * earningsDollars);
    }

    public Double getEarningsDollars() {
        return earningsCents.doubleValue() / 100;
    }

}
