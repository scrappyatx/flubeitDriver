/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.layoutComponents;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

import static android.text.format.DateUtils.FORMAT_SHOW_DATE;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class LayoutComponentUtilities {
    public static final String TAG = "LayoutComponentUtilities";

    public static String getDisplayDate(Context context, Date startTime){
        //String relativeTime = String.valueOf(DateUtils.getRelativeDateTimeString(context, startTime.getTime(), DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_SHOW_DATE));

        //String relativeTime = String.valueOf(DateUtils.getRelativeTimeSpanString(context, startTime.getTime(),false ));
        String relativeTime = String.valueOf(DateUtils.getRelativeTimeSpanString(startTime.getTime(), System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS));
        Timber.tag(TAG).d("startTime    = " + startTime);
        Timber.tag(TAG).d("relativeTime = " + relativeTime);
        return relativeTime;
    }

    public static String getStartTime(Date startTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm aa", Locale.US);
        return dateFormat.format(startTime);
    }

    public static String getDisplayExpiry(Context context, Date expiryTime){
        String relativeTime = String.valueOf(DateUtils.getRelativeDateTimeString(context, expiryTime.getTime(), DateUtils.HOUR_IN_MILLIS, DateUtils.DAY_IN_MILLIS,0));
        Timber.tag(TAG).d("expiryTime    = " + expiryTime);
        Timber.tag(TAG).d("relativeTime = " + relativeTime);
        return relativeTime;
    }

    public static String getDisplayTiming(Date startTime, Date endTime){
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm aa", Locale.US);
        String formattedStartTime = dateFormat.format(startTime);
        String formattedEndTime = dateFormat.format(endTime);

        return formattedStartTime + " - " + formattedEndTime;
    }

    public static String getDisplayDuration(Date startTime, Date endTime){
        /// milliseconds
        Long difference = endTime.getTime() - startTime.getTime();
        Timber.tag(TAG).d("endTime    = " + endTime);
        Timber.tag(TAG).d("startTime  = " + startTime);
        Timber.tag(TAG).d("difference = " + difference);

        Long secondsInMilli = 1000L;
        Long minutesInMilli = secondsInMilli * 60;
        Long hoursInMilli = minutesInMilli * 60;
        Long daysInMilli = hoursInMilli * 24;

        //long elapsedDays = different / daysInMilli;
        //different = different % daysInMilli;

        Long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;

        Long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        Long elapsedSeconds = difference / secondsInMilli;

        Timber.tag(TAG).d("duration  = " + elapsedHours + " hours " + elapsedMinutes + " minutes");
        return elapsedHours.toString() + " hrs " + elapsedMinutes + " min";
    }
}
