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

    private static final String DIAL_PHONE_NUMBER_FORMAT = "[0-9]{10}";
    private static final String DISPLAY_PHONE_NUMBER_FORMAT = "(%s) %s-%s";

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

    public static Boolean isDialPhoneNumberFormattedProperly(String dialPhoneNumber){
        return dialPhoneNumber.matches(DIAL_PHONE_NUMBER_FORMAT);
    }

    public static String getFormattedDisplayPhoneNumber(String dialPhoneNumber){
        if (dialPhoneNumber.matches(DIAL_PHONE_NUMBER_FORMAT)){
            return String.format(DISPLAY_PHONE_NUMBER_FORMAT,
                            dialPhoneNumber.substring(0,3),
                            dialPhoneNumber.substring(3, 6),
                            dialPhoneNumber.substring(6,10)
                    );
        } else {
            return null;
        }
    }

}
