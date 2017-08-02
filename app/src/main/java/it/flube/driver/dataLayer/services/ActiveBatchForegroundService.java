/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.rollbar.android.Rollbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import it.flube.driver.R;
import it.flube.driver.dataLayer.messaging.RealtimeMessaging;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OffersAvailableResponseHandler;
import timber.log.Timber;


/**
 * Created on 6/11/2017
 * Project : Driver
 */

public class ActiveBatchForegroundService extends Service {
    private static final String TAG = "ActiveBatchForegroundService";
    private static final int SERVICE_ID = 101;



    @Override
    public void onCreate() {
        super.onCreate();
        //register on eventbus
        //EventBus.getDefault().register(this);
        Timber.tag(TAG).d("onCreate()");
    }

    @Override
    public void onDestroy() {
        //unregister on eventbus
        EventBus.getDefault().unregister(this);
        Timber.tag(TAG).d("onDestroy()");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //used only in case of bound services
        return null;
    }


    public static Intent startIntent(Context context) {
        Intent i = new Intent(context,ActiveBatchForegroundService.class);
        i.putExtra("action", "start");
        return i;
    }

    public static Intent shutdownIntent(Context context) {
        Intent i = new Intent(context,ActiveBatchForegroundService.class);
        i.putExtra("action", "shutdown");
        return i;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.tag(TAG).d("onStartCommand() --> flags : " + Integer.toString(flags) + " startId : " + Integer.toString(startId));
        handleIntent(intent);
        return START_STICKY;
    }

    ///
    ///     commands are sent via intents
    ///
    private void handleIntent(Intent i) {
        if (i.hasExtra("action")) {
            //there is an action specified in this intent, do something based on action
            String action = i.getStringExtra("action");
            Timber.tag(TAG).d(TAG,"handled action --> " + action);
            switch (action) {
                case "start":
                    //start service in foreground
                    startForeground(SERVICE_ID, getNotification());



                    break;
                case "shutdown":

                    stopForeground(true);
                    stopSelf(SERVICE_ID);
                    break;
                case "gotoHome":

                    break;
                default:
                    Timber.tag(TAG).w("unhandled action --> " + action);
                    break;
            }
        } else {
            //no action specified in this intent, do nothing
            Timber.tag(TAG).w("intent with no action provided");
        }
    }

    private Notification getNotification() {

        //
        //   Prepare intent which is triggered if the notification is selected
        //

        Intent i = new Intent(getApplicationContext(), ActiveBatchForegroundService.class);
        // set the action to go to home activity
        i.putExtra("action", "gotoHome");
        // create pending intent.  Uses system time in milliseconds to have a unique ID for the pending intent
        PendingIntent pi = PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(), i, 0);

        // scale the icon to use as large icon in notification
        int height = (int) getResources().getDimension(android.R.dimen.notification_large_icon_height);
        int width = (int) getResources().getDimension(android.R.dimen.notification_large_icon_width);
        Bitmap b = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo_round), width, height, false);

        // now create the notification
        Notification n = new NotificationCompat.Builder(getApplicationContext())
                .setLargeIcon(b)
                .setSmallIcon(R.drawable.app_logo_round)
                .setContentTitle(getResources().getString(R.string.rtm_and_loc_updates_fg_svc_notification_title))
                .setContentText(getResources().getString(R.string.rtm_and_loc_updates_fg_svc_notification_text_searching))
                .setContentIntent(pi)
                .build();

        //make small icon invisible
        int smallIconId = getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
        if (smallIconId != 0) {
            //n.setCustomContentView().setViewVisibility(smallIconId, View.INVISIBLE);
            //n.setCustomBigContentView().setViewVisibility(smallIconId, View.INVISIBLE);
        }
        return n;
    }

    /////
    /////   Message Received Events
    /////
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OffersAvailableResponseHandler.AvailableOffersEvent msg) {
        Timber.tag(TAG).d("received " + Integer.toString(msg.getOfferList().size()) + " offers");
    }

}
