/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseStartBatchDemoRequest;
import timber.log.Timber;

/**
 * Created on 8/20/2017
 * Project : Driver
 */

public class StartDemoBatchResponseHandler implements UseCaseStartBatchDemoRequest.Response {
    private final static String TAG = "StartDemoBatchResponseHandler";

    public void startBatchDemoComplete(Batch activeBatch) {
        Timber.tag(TAG).d("startBatchDemoComplete -> " + activeBatch.getGUID());
        EventBus.getDefault().postSticky(new StartDemoBatchResponseHandler.UseCaseStartDemoBatchEvent(activeBatch));
    }

    public static class UseCaseStartDemoBatchEvent {
        private Batch batch;

        public UseCaseStartDemoBatchEvent(Batch batch){
            this.batch = batch;
        }

        public Batch getBatch(){
            return batch;
        }
    }

}
