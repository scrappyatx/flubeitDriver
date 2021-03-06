/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.activeBatchForegroundService;

import android.content.Context;
import android.content.Intent;

import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
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

    public void startActiveBatchForegroundServiceRequest(String clientId, String batchGuid, String serviceOrderGuid, String orderStepGuid, String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType,
                                                  StartActiveBatchForegroundServiceResponse response){

        Timber.tag(TAG).d("starting ActiveBatchForegroundService...");

        Intent i = IntentUtilities.getStartIntent(appContext, clientId, batchGuid, serviceOrderGuid, orderStepGuid, notificationText, notificationSubText, taskType);
        Timber.tag(TAG).d("   ...made start intent");

        appContext.startService(i);
        Timber.tag(TAG).d("...COMPLETE");
        response.activeBatchForegroundServiceStarted();
    }


    public void updateActiveBatchForegroundServiceRequest(String clientId, String batchGuid, String serviceOrderGuid, String orderStepGuid, String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType,
                                                   UpdateActiveBatchForegroundServiceResponse response){
        Timber.tag(TAG).d("updating ActiveBatchForegroundService...");

        Intent i = IntentUtilities.getUpdateIntent(appContext, clientId, batchGuid, serviceOrderGuid, orderStepGuid, notificationText, notificationSubText, taskType);
        Timber.tag(TAG).d("   ...made update intent");

        appContext.startService(i);
        Timber.tag(TAG).d("...COMPLETE");
        response.activeBatchForegroundServiceUpdated();
    }

    public void stopActiveBatchForegroundServiceRequest(StopActiveBatchForegroundServiceResponse response){
        Timber.tag(TAG).d("stopping ActiveBatchForegroundService...");

        Intent i = IntentUtilities.getShutdownIntent(appContext);
        Timber.tag(TAG).d("   ...made shutdown intent");

        appContext.startService(i);
        Timber.tag(TAG).d("...COMPLETE");
        response.activeBatchForegroundServiceStopped();
    }
}
