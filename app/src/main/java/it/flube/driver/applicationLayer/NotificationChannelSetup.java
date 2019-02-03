/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.applicationLayer;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import it.flube.driver.R;
import timber.log.Timber;

import static android.media.AudioAttributes.USAGE_NOTIFICATION;
import static android.media.AudioManager.STREAM_NOTIFICATION;
import static it.flube.driver.applicationLayer.ApplicationConstants.CHANNEL_ID_ACTIVE_BATCH;
import static it.flube.driver.applicationLayer.ApplicationConstants.CHANNEL_ID_PERSONAL_OFFERS;
import static it.flube.driver.applicationLayer.ApplicationConstants.CHANNEL_ID_PUBLIC_OFFERS;
import static it.flube.driver.applicationLayer.ApplicationConstants.CHANNEL_PUBLIC_OFFERS_SOUNDFILE;

/**
 * Created on 1/27/2019
 * Project : Driver
 */
public class NotificationChannelSetup {
    public static final String TAG = "NotificationChannelSetup";


    public void createNotificationChannel(Context appContext){

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Timber.tag(TAG).d("creating notification channels");

            NotificationManager notificationManager = (NotificationManager) appContext.getSystemService(Context.NOTIFICATION_SERVICE);
            setupActiveBatchChannel(appContext, notificationManager);
            setupPublicOffersChannel(appContext, notificationManager);
            setupPersonalOffersChannel(appContext, notificationManager);

        } else {
            Timber.tag(TAG).d("not building any notification channels");
        }
    }

    @TargetApi(26)
    private void setupActiveBatchChannel(Context appContext, NotificationManager notificationManager){
        /// set the channel fields
        CharSequence name = appContext.getString(R.string.active_batch_notification_channel_name);
        String description = appContext.getString(R.string.active_batch_notification_channel_description);
        int importance = NotificationManager.IMPORTANCE_LOW;

        // create the channel
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_ACTIVE_BATCH, name, importance);
        channel.enableLights(true);
        channel.setLightColor(Color.RED);

        try {
            notificationManager.createNotificationChannel(channel);
        } catch (Exception e){
            Timber.tag(TAG).e(e);
        }
    }

    @TargetApi(26)
    private void setupPublicOffersChannel(Context appContext, NotificationManager notificationManager){
        /// set the channel fields
        CharSequence name = appContext.getString(R.string.public_offer_notification_channel_name);
        String description = appContext.getString(R.string.public_offer_notification_channel_description);
        int importance = NotificationManager.IMPORTANCE_MIN;

        // create the channel
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_PUBLIC_OFFERS, name, importance);

        Uri uri= Uri.parse("android.resource://"+appContext.getPackageName()+"/raw/" + CHANNEL_PUBLIC_OFFERS_SOUNDFILE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setFlags(0)
                .setLegacyStreamType(STREAM_NOTIFICATION)
                .setUsage(USAGE_NOTIFICATION)
                .build();

        channel.setSound(uri, audioAttributes);


        try {
            notificationManager.createNotificationChannel(channel);
        } catch (Exception e){
            Timber.tag(TAG).e(e);
        }
    }

    @TargetApi(26)
    private void setupPersonalOffersChannel(Context appContext, NotificationManager notificationManager){

        /// set the channel fields
        CharSequence name = appContext.getString(R.string.personal_offer_notification_channel_name);
        String description = appContext.getString(R.string.personal_offer_notification_channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        // create the channel
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_PERSONAL_OFFERS, name, importance);

        Uri uri= Uri.parse("android.resource://"+appContext.getPackageName()+"/raw/" + CHANNEL_PUBLIC_OFFERS_SOUNDFILE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setFlags(0)
                .setLegacyStreamType(STREAM_NOTIFICATION)
                .setUsage(USAGE_NOTIFICATION)
                .build();

        channel.setSound(uri, audioAttributes);


        try {
            notificationManager.createNotificationChannel(channel);
        } catch (Exception e){
            Timber.tag(TAG).e(e);
        }
    }

}
