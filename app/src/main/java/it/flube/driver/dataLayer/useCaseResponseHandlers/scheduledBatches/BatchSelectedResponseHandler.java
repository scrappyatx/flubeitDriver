/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.entities.BatchCloudDB;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseBatchSelected;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class BatchSelectedResponseHandler implements UseCaseBatchSelected.Response {
    private final static String TAG = "BatchSelectedResponseHandler";

    public void batchSelected(BatchCloudDB batch) {
        Timber.tag(TAG).d("offer Selected");
        EventBus.getDefault().postSticky(new BatchSelectedResponseHandler.UseCaseBatchSelectedEvent(batch));
    }


    public static class UseCaseBatchSelectedEvent {
        private BatchCloudDB batch;
        public UseCaseBatchSelectedEvent(BatchCloudDB batch){
            this.batch = batch;
        }

        public BatchCloudDB getBatch(){
            return batch;
        }
    }
}
