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

    public void forfeitBatchComplete(String batchGuid) {
        Timber.tag(TAG).d("forfeitBatchComplete -> " + batchGuid);
        EventBus.getDefault().postSticky(new UseCaseForfeitBatchEvent(batchGuid));
    }

    public static class UseCaseForfeitBatchEvent {
        private String batchGuid;

        public UseCaseForfeitBatchEvent(String batchGuid){
            this.batchGuid = batchGuid;
        }

        public String getBatchGuid(){
            return batchGuid;
        }
    }

}
