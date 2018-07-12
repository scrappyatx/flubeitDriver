/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import timber.log.Timber;

/**
 * Created on 7/10/2018
 * Project : Driver
 */
public class SendTextMessage {
    public static final String TAG = "SendTextMessage";

    private static final String SMS_PREFIX = "smsto:";

    public void sendTextRequest(AppCompatActivity activity, String dialNumber){
        Timber.tag(TAG).d("sendTextRequest, dialNumber -> " + dialNumber);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse(SMS_PREFIX + dialNumber));

        if (smsIntent.resolveActivity(activity.getPackageManager()) == null){
            Timber.tag(TAG).d("...couldn't resolve intent, do nothing");
        } else {
            Timber.tag(TAG).d("...resolved intent, send it");
            activity.startActivity(smsIntent);
        }
    }

}
