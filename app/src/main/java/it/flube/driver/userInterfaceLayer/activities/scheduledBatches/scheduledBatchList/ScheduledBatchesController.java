/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.BatchSelectedResponseHandler;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseBatchSelected;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class ScheduledBatchesController implements BatchListAdapter.Response {
    private final String TAG = "SchedBatchesController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public ScheduledBatchesController() {
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
        Timber.tag(TAG).d("controller CREATED");
    }

    public void batchSelected(Batch batch) {
        Timber.tag(TAG).d("batch Selected --> " + batch.getGuid());
        useCaseExecutor.execute(new UseCaseBatchSelected(device, batch, new BatchSelectedResponseHandler()));
    }

    public void close() {
        useCaseExecutor = null;
        device = null;
        Timber.tag(TAG).d("controller CLOSED");
    }

}
