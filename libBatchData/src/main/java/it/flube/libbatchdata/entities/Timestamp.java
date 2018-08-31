/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.Date;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class Timestamp {
    private Long scheduledTime;
    private Long actualTime;

    public Long getScheduledTime(){
        return scheduledTime;
    }

    public void setScheduledTime(Long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Long getActualTime(){
        return actualTime;
    }

    public void setActualTime(Long actualTime){
        this.actualTime = actualTime;
    }
}
