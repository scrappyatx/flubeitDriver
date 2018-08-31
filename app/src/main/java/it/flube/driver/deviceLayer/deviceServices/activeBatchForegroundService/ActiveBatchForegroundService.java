/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.activeBatchForegroundService;

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


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.deviceEvents.LocationTrackingPositionChangedEvent;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.AuthorizePaymentActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.GiveAssetActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.ReceiveAssetActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.userTriggerStep.UserTriggerActivity;
import it.flube.libbatchdata.entities.LatLonLocation;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseLocationUpdateRequest;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeActivity;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.deviceServices.activeBatchForegroundService.IntentUtilities.ACTION_GOTO_ACTIVE_BATCH;
import static it.flube.driver.deviceLayer.deviceServices.activeBatchForegroundService.IntentUtilities.ACTION_SHUTDOWN;
import static it.flube.driver.deviceLayer.deviceServices.activeBatchForegroundService.IntentUtilities.ACTION_START;
import static it.flube.driver.deviceLayer.deviceServices.activeBatchForegroundService.IntentUtilities.ACTION_UPDATE;


/**
 * Created on 6/11/2017
 * Project : Driver
 */

public class ActiveBatchForegroundService extends Service
        implements UseCaseLocationUpdateRequest.Response {
    private static final String TAG = "ActiveBatchForegroundService";
    private static final int SERVICE_ID = 101;

    private String clientId;
    private String batchGuid;
    private String serviceOrderGuid;
    private String orderStepGuid;
    private Boolean haveAllBatchData;
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


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.tag(TAG).d("onStartCommand() --> flags : " + Integer.toString(flags) + " startId : " + Integer.toString(startId));
        if (intent != null) {
            Timber.tag(TAG).d("   ...handling intent");
            handleIntent(intent);
        } else {
            Timber.tag(TAG).d("   ...started with null intent");
        }
        return START_STICKY;
    }

    ///
    ///     commands are sent via intents
    ///
    private void handleIntent(Intent i) {
        Timber.tag(TAG).d("handling intent...");
        String action = IntentUtilities.getIntentAction(i);
        if (action != null) {
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
                .setContentIntent(IntentUtilities.getNotificationClickIntent(this, taskType))
                .build();

        //make small icon invisible
        //int smallIconId = getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
        //if (smallIconId != 0) {
            //n.setCustomContentView().setViewVisibility(smallIconId, View.INVISIBLE);
            //n.setCustomBigContentView().setViewVisibility(smallIconId, View.INVISIBLE);
        //}
    }

    private void selfStart(Intent i){
        ///build a notification
        Notification n = getNotification(IntentUtilities.getExtraNotificationText(this, i), IntentUtilities.getExtraNotificationSubtext(this, i), IntentUtilities.getExtraTaskType(i));

        // start the service with the notification
        startForeground(SERVICE_ID, n);

        // save all the batch, service order & step info
        isActive = true;
        getClientAndBatchInfo(i);
        Timber.tag(TAG).d("   ...started foreground service");
    }

    private void selfStop(Intent i){
        //clear all batch variables
        isActive = false;
        clearClientAndBatchInfo();
        // stop service & remove notification
        stopForeground(true);
        stopSelf(SERVICE_ID);
        Timber.tag(TAG).d("   ...stopped foreground service");
    }

    private void selfUpdate(Intent i){
        //update client and batch info
        getClientAndBatchInfo(i);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = getNotification(IntentUtilities.getExtraNotificationText(this, i), IntentUtilities.getExtraNotificationSubtext(this, i), IntentUtilities.getExtraTaskType(i));
        try {
            notificationManager.notify(SERVICE_ID, n);
        } catch (Exception e){
            Timber.tag(TAG).w("exception trying to update active batch notification");
            Timber.tag(TAG).e(e);
        }
        Timber.tag(TAG).d("   ...updating notification");
    }

    private void getClientAndBatchInfo(Intent i){
        clientId = IntentUtilities.getExtraClientId(i);
        batchGuid = IntentUtilities.getExtraBatchGuid(i);
        serviceOrderGuid = IntentUtilities.getExtraBatchGuid(i);
        orderStepGuid = IntentUtilities.getExtraOrderStepGuid(i);

        haveAllBatchData = ((batchGuid != null) && (serviceOrderGuid != null) && (orderStepGuid != null) && (clientId != null));
    }

    private void clearClientAndBatchInfo(){
        clientId = null;
        batchGuid = null;
        serviceOrderGuid = null;
        orderStepGuid = null;
    }


    private Bitmap getLargeIcon(){
        // scale the icon to use as large icon in notification
        int height = (int) getResources().getDimension(android.R.dimen.notification_large_icon_height);
        int width = (int) getResources().getDimension(android.R.dimen.notification_large_icon_width);
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo_round), width, height, false);
    }


    public void useCaseLocationUpdateComplete(){
        Timber.tag(TAG).d("useCaseLocationUpdateComplete");
    }

    @org.greenrobot.eventbus.Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(LocationTrackingPositionChangedEvent event) {
        Timber.tag(TAG).d("got a location update");
        LatLonLocation location = event.getPosition();

        if (isActive){
            if (haveAllBatchData){
                AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseLocationUpdateRequest(AndroidDevice.getInstance(), batchGuid, serviceOrderGuid, orderStepGuid, location, this));
            }
        }

        //device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseLocationUpdateRequest(device, batchGuid, serviceOrderGuid, orderStepGuid, location));
        //AndroidDevice.getInstance().getRealtimeActiveBatchMessages()
        //        .sendMsgLocationUpdate(event.getPosition().getLatitude(), event.getPosition().getLongitude());
    }
}
