/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

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

    public static Date getFutureDate(@NonNull Integer minutesToAdd){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, minutesToAdd);
        return cal.getTime();
    }

    public static Date addMinutesToDate(@NonNull Date initialDate, @NonNull Integer minutesToAdd){
        Calendar cal = Calendar.getInstance();
        cal.setTime(initialDate);
        cal.add(Calendar.MINUTE, minutesToAdd);
        return cal.getTime();
    }

}
