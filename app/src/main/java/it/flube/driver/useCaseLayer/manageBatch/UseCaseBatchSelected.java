/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class UseCaseBatchSelected implements
        Runnable,
        CloudDatabaseInterface.GetBatchDetailResponse {

    private final MobileDeviceInterface device;
    private final UseCaseBatchSelected.Response response;
    private final Batch batch;

    public UseCaseBatchSelected(MobileDeviceInterface device, Batch batch, UseCaseBatchSelected.Response response) {
        this.device = device;
        this.response = response;
        this.batch = batch;
    }

    public void run(){
        // get the batch detail that corresponse to this offer
        device.getCloudDatabase().getBatchDetailRequest(batch.getGuid(), this);
    }

    public void cloudDatabaseGetBatchDetailSuccess(BatchDetail batchDetail){
        response.batchSelectedSuccess(batchDetail);
    }

    public void cloudDatabaseGetBatchDetailFailure() {
        response.batchSelectedFailure();
    }

    public interface Response {
        void batchSelectedSuccess(BatchDetail batchDetail);

        void batchSelectedFailure();
    }
}
