/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.OfferSelectedResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.BatchSelectedResponseHandler;
import it.flube.driver.modelLayer.entities.BatchCloudDB;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseBatchSelected;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class ScheduledBatchesController implements BatchListAdapter.Response  {
    private final String TAG = "SchedBatchesController";
    private ExecutorService useCaseExecutor;


    public ScheduledBatchesController() {
        Timber.tag(TAG).d("controller CREATED");
        useCaseExecutor = Executors.newSingleThreadExecutor();
    }

    public void batchSelected(BatchCloudDB batch) {
        Timber.tag(TAG).d("batch Selected --> " + batch.getOrderOID());
        useCaseExecutor.execute(new UseCaseBatchSelected(batch, new BatchSelectedResponseHandler()));
    }

    public void close() {
        useCaseExecutor.shutdown();
        Timber.tag(TAG).d("controller CLOSED");
    }

}
