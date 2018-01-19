/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.userInterfaceLayer.userInterfaceEvents.demoBatch.DemoBatchStartedEvent;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseStartDemoBatchRequest;
import timber.log.Timber;

/**
 * Created on 8/20/2017
 * Project : Driver
 */

public class StartDemoBatchResponseHandler implements UseCaseStartDemoBatchRequest.Response {
    private final static String TAG = "StartDemoBatchResponseHandler";

    public void startDemoBatchComplete(String batchGuid) {
        Timber.tag(TAG).d("startBatchDemoComplete -> " + batchGuid);
        EventBus.getDefault().postSticky(new DemoBatchStartedEvent());
    }
}
