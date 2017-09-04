/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseForfeitBatchRequest;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class ForfeitBatchResponseHandler implements UseCaseForfeitBatchRequest.Response {
    private final static String TAG = "ForfeitBatchResponseHandler";

    public void forfeitBatchComplete(BatchCloudDB batch) {
        Timber.tag(TAG).d("forfeitBatchComplete -> " + batch.getOrderOID());
        EventBus.getDefault().postSticky(new UseCaseForfeitBatchEvent(batch));
    }

    public static class UseCaseForfeitBatchEvent {
        private BatchCloudDB batch;

        public UseCaseForfeitBatchEvent(BatchCloudDB batch){
            this.batch = batch;
        }

        public BatchCloudDB getBatch(){
            return batch;
        }
    }

}
