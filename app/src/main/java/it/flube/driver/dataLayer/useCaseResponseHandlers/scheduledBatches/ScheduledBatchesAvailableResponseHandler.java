/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 7/25/2017
 * Project : Driver
 */

public class ScheduledBatchesAvailableResponseHandler implements CloudDatabaseInterface.ScheduledBatchesUpdated {
    private static final String TAG = "ScheduledBatchesAvailableResponseHandler";

    public void cloudDatabaseScheduledBatchesUpdated(ArrayList<BatchCloudDB> batchList) {
        Timber.tag(TAG).d("scheduled batches available from cloud database");
        EventBus.getDefault().postSticky(new ScheduledBatchesAvailableResponseHandler.ScheduledBatchUpdateEvent(batchList));
    }

    public static class ScheduledBatchUpdateEvent {
        private ArrayList<BatchCloudDB> batchList;
        private int batchCount;

        public ScheduledBatchUpdateEvent(ArrayList<BatchCloudDB> batchList){
            this.batchList = batchList;
            this.batchCount = batchList.size();
        }

        public ArrayList<BatchCloudDB> getBatchList() {
            return batchList;
        }

        public int getBatchCount() {
            return batchCount;
        }

    }

}
