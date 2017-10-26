/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseBatchSelected;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class BatchSelectedResponseHandler implements UseCaseBatchSelected.Response {
    private final static String TAG = "BatchSelectedResponseHandler";

    public void batchSelectedSuccess(BatchDetail batchDetail) {
        Timber.tag(TAG).d("got batch detail data for batch : " + batchDetail.getBatchGuid());
        EventBus.getDefault().postSticky(new BatchSelectedResponseHandler.UseCaseBatchSelectedEvent(batchDetail));
    }

    public void batchSelectedFailure(){
        Timber.tag(TAG).w("couldn't find batchDetail for the selected batch");
        //TODO show user an alert that the batch detail can't be shown, that some non-fixable error occured
    }

    public static class UseCaseBatchSelectedEvent {
        private BatchDetail batchDetail;
        public UseCaseBatchSelectedEvent(BatchDetail batchDetail){
            this.batchDetail = batchDetail;
        }

        public BatchDetail getBatchDetail(){
            return batchDetail;
        }
    }
}
