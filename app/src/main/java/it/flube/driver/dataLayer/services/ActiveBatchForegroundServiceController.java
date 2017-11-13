/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.services;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class ActiveBatchForegroundServiceController implements
        ActiveBatchForegroundServiceInterface {

    private static final String TAG = "ActiveBatchForegroundServiceController";

    private final Context appContext;

    public ActiveBatchForegroundServiceController(Context appContext){
        this.appContext = appContext;
    }

    public void startActiveBatchForegroundService(String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType){
        Timber.tag(TAG).d("starting ActiveBatchForegroundService...");

        Intent i = ActiveBatchForegroundService.startIntent(appContext, notificationText, notificationSubText, taskType);
        Timber.tag(TAG).d("   ...made start intent");

        appContext.startService(i);
        Timber.tag(TAG).d("...COMPLETE");
    }


    public void updateActiveBatchForegroundService(String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType){
        Timber.tag(TAG).d("updating ActiveBatchForegroundService...");

        Intent i = ActiveBatchForegroundService.updateIntent(appContext, notificationText, notificationSubText, taskType);
        Timber.tag(TAG).d("   ...made update intent");

        appContext.startService(i);
        Timber.tag(TAG).d("...COMPLETE");
    }

    public void stopActiveBatchForegroundService(){
        Timber.tag(TAG).d("stopping ActiveBatchForegroundService...");

        Intent i = ActiveBatchForegroundService.shutdownIntent(appContext);
        Timber.tag(TAG).d("   ...made shutdown intent");

        appContext.startService(i);
        Timber.tag(TAG).d("...COMPLETE");
    }
}
