/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created on 9/19/2017
 * Project : Driver
 */

public class BuilderUtilities {

    public static String generateGuid(){
        return UUID.randomUUID().toString();
    }

    public static Date getNowDate(){
        return Calendar.getInstance().getTime();
    }

    public static Date getFutureDate(Integer minutesToAdd){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutesToAdd);
        return cal.getTime();
    }

    public static Date addMinutesToDate(Date initialDate, Integer minutesToAdd){
        Calendar cal = Calendar.getInstance();
        cal.setTime(initialDate);
        cal.add(Calendar.MINUTE, minutesToAdd);
        return cal.getTime();
    }

}
