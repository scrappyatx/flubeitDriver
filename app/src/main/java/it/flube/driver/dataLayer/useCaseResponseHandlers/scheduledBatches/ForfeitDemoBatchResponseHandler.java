/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.userInterfaceEvents.scheduledBatch.BatchForfeitedEvent;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseForfeitDemoBatchRequest;
import timber.log.Timber;

/**
 * Created on 10/13/2017
 * Project : Driver
 */

public class ForfeitDemoBatchResponseHandler implements
        UseCaseForfeitDemoBatchRequest.Response {

    private final static String TAG = "ForfeitDemoBatchResponseHandler";

    public ForfeitDemoBatchResponseHandler(){

    }

    public void useCaseForfeitDemoBatchComplete(String batchGuid) {
        Timber.tag(TAG).d("forfeit complete -> batchGuid : " + batchGuid);
        EventBus.getDefault().postSticky(new BatchForfeitedEvent());
    }

}
