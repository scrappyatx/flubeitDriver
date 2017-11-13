/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


import com.google.common.eventbus.Subscribe;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.deviceEvents.LocationTrackingPositionChangedEvent;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeNoActiveBatchActivity;
import timber.log.Timber;


/**
 * Created on 6/11/2017
 * Project : Driver
 */

public class ActiveBatchForegroundService extends Service {
    private static final String TAG = "ActiveBatchForegroundService";
    private static final int SERVICE_ID = 101;

    private static final String EXTRA_ACTION = "action";
    private static final String EXTRA_NOTIFICATION_TEXT = "notificationText";
    private static final String EXTRA_NOTIFICATION_SUBTEXT = "notificationSubText";
    private static final String EXTRA_TASK_TYPE = "taskType";

    private static final String ACTION_START = "start";
    private static final String ACTION_SHUTDOWN = "shutdown";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_GOTO_ACTIVE_BATCH = "goto";

    private Boolean isActive;

    @Override
    public void onCreate() {
        super.onCreate();
        isActive = false;
        EventBus.getDefault().register(this);
        Timber.tag(TAG).d("onCreate()");
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        Timber.tag(TAG).d("onDestroy()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        //used only in case of bound services
        return null;
    }


    public static Intent startIntent(Context appContext, String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType) {
        Intent i = new Intent(appContext,ActiveBatchForegroundService.class);
        i.putExtra(EXTRA_ACTION, ACTION_START);
        i.putExtra(EXTRA_NOTIFICATION_TEXT, notificationText);
        i.putExtra(EXTRA_NOTIFICATION_SUBTEXT, notificationSubText);
        i.putExtra(EXTRA_TASK_TYPE, taskType);
        return i;
    }

    public static Intent shutdownIntent(Context appContext) {
        Intent i = new Intent(appContext,ActiveBatchForegroundService.class);
        i.putExtra(EXTRA_ACTION, ACTION_SHUTDOWN);
        return i;
    }

    public static Intent updateIntent(Context appContext, String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType){
        //Intent i = new Intent(context, ActiveBatchForegroundService.class);
        Intent i = new Intent(appContext, ActiveBatchForegroundService.class);
        i.putExtra(EXTRA_ACTION, ACTION_UPDATE);
        i.putExtra(EXTRA_NOTIFICATION_TEXT, notificationText);
        i.putExtra(EXTRA_NOTIFICATION_SUBTEXT, notificationSubText);
        i.putExtra(EXTRA_TASK_TYPE, taskType);
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
        Timber.tag(TAG).d("handling intent...");
        if (i.hasExtra(EXTRA_ACTION)) {

            String action = i.getStringExtra(EXTRA_ACTION);
            Timber.tag(TAG).d("   ...action --> " + action);

            switch (action) {
                case ACTION_START:
                    if (isActive){
                        Timber.tag(TAG).d("      ...foreground already started, just need to update");
                        selfUpdate(i);
                    } else {
                        selfStart(i);
                    }
                    break;

                case ACTION_SHUTDOWN:
                    selfStop(i);
                    break;

                case ACTION_UPDATE:
                    if (isActive){
                        selfUpdate(i);
                    } else {
                        Timber.tag(TAG).d("      ...foreground not started, need to start it");
                        selfStart(i);
                    }
                    break;

                case ACTION_GOTO_ACTIVE_BATCH:
                    Timber.tag(TAG).d("   ...notification clicked.  user wants to go to active batch");
                    break;

                default:
                    Timber.tag(TAG).w("   ...unhandled action --> " + action);
                    break;
            }
        } else {
            //no action specified in this intent, do nothing
            Timber.tag(TAG).w("intent with no action provided");
        }
    }

    private Notification getNotification(String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType) {

        return new NotificationCompat.Builder(getApplicationContext(),null)
                .setLargeIcon(getLargeIcon())
                .setSmallIcon(R.drawable.app_logo_round)
                .setContentTitle(getResources().getString(R.string.active_batch_service_notification_title))
                .setSubText(notificationSubText)
                .setContentText(notificationText)
                .setContentIntent(getNotificationClickIntent(taskType))
                .build();

        //make small icon invisible
        //int smallIconId = getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
        //if (smallIconId != 0) {
            //n.setCustomContentView().setViewVisibility(smallIconId, View.INVISIBLE);
            //n.setCustomBigContentView().setViewVisibility(smallIconId, View.INVISIBLE);
        //}
    }

    private void selfStart(Intent i){
        Notification n = getNotification(getExtraNotificationText(i), getExtraNotificationSubtext(i), getExtraTaskType(i));
        startForeground(SERVICE_ID, n);
        isActive = true;
        Timber.tag(TAG).d("   ...started foreground service");
    }

    private void selfStop(Intent i){
        isActive = false;
        stopForeground(true);
        stopSelf(SERVICE_ID);
        Timber.tag(TAG).d("   ...stopped foreground service");
    }

    private void selfUpdate(Intent i){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = getNotification(getExtraNotificationText(i), getExtraNotificationSubtext(i), getExtraTaskType(i));
        try {
            notificationManager.notify(SERVICE_ID, n);
        } catch (Exception e){
            Timber.tag(TAG).w("exception trying to update active batch notification");
            Timber.tag(TAG).e(e);
        }
        Timber.tag(TAG).d("   ...updating notification");
    }

    private String getExtraNotificationText(Intent i){
        String notificationText;

        Timber.tag(TAG).w("      getting notification text...");

        if (i.hasExtra(EXTRA_NOTIFICATION_TEXT)){
            notificationText = i.getStringExtra(EXTRA_NOTIFICATION_TEXT);
            Timber.tag(TAG).d("         ...got from intent extra");
        } else {
            notificationText = getResources().getString(R.string.active_batch_service_notification_text);
            Timber.tag(TAG).w("         ...no intent extra, got default from resource");
        }

        Timber.tag(TAG).w("      ...notificationText = " + notificationText);
        return notificationText;
    }

    private String getExtraNotificationSubtext(Intent i){
        String notificationSubText;

        Timber.tag(TAG).w("      getting notification subText...");

        if (i.hasExtra(EXTRA_NOTIFICATION_SUBTEXT)){
            notificationSubText = i.getStringExtra(EXTRA_NOTIFICATION_SUBTEXT);
            Timber.tag(TAG).d("         ...got from intent extra");
        } else {
            notificationSubText = getResources().getString(R.string.active_batch_service_notification_subtext);
            Timber.tag(TAG).w("         ...no intent extra, got default from resource");
        }

        Timber.tag(TAG).w("      ...notificationSubText = " + notificationSubText);
        return notificationSubText;
    }

    private OrderStepInterface.TaskType getExtraTaskType(Intent i){
        OrderStepInterface.TaskType taskType;
        Timber.tag(TAG).w("      getting task type...");

        if (i.hasExtra(EXTRA_TASK_TYPE)){
            taskType = (OrderStepInterface.TaskType) i.getSerializableExtra(EXTRA_TASK_TYPE);
            Timber.tag(TAG).d("         ...got from intent extra");
        } else {
            taskType = OrderStepInterface.TaskType.WAIT_FOR_USER_TRIGGER;
            Timber.tag(TAG).w("         ...no intent extra, just picked one for default");
        }

        Timber.tag(TAG).w("      ...taskType = " + taskType.toString());
        return taskType;
    }

    private Bitmap getLargeIcon(){
        // scale the icon to use as large icon in notification
        int height = (int) getResources().getDimension(android.R.dimen.notification_large_icon_height);
        int width = (int) getResources().getDimension(android.R.dimen.notification_large_icon_width);
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo_round), width, height, false);
    }

    private PendingIntent getNotificationClickIntent(OrderStepInterface.TaskType taskType){
        //Intent i = new Intent(getApplicationContext(), ActiveBatchForegroundService.class);
        //i.putExtra(EXTRA_ACTION, ACTION_GOTO_ACTIVE_BATCH);
        // create pending intent.  Uses system time in milliseconds to have a unique ID for the pending intent
        //return PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(), i, 0);

        Intent i;
        switch (taskType){
            case NAVIGATION:
                i = new Intent(getApplicationContext(), NavigationActivity.class);
                break;
            case TAKE_PHOTOS:
                i = new Intent(getApplicationContext(), PhotoActivity.class);
                break;
            default:
                i = new Intent(getApplicationContext(), HomeNoActiveBatchActivity.class);
                break;
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return PendingIntent.getActivity(this, (int) System.currentTimeMillis(), i,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(LocationTrackingPositionChangedEvent event) {
        Timber.tag(TAG).d("got a location update");
        AndroidDevice.getInstance().getRealtimeActiveBatchMessages()
                .sendMsgLocationUpdate(event.getPosition().getLatitude(), event.getPosition().getLongitude());
    }
}
