/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import java.util.Date;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class Timestamp {
    private Date scheduledTime;
    private Date actualTime;

    public Date getScheduledTime(){
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Date getActualTime(){
        return actualTime;
    }

    public void setActualTime(Date actualTime){
        this.actualTime = actualTime;
    }
}
