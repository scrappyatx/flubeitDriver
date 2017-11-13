/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class UseCaseBatchFinishedRequest implements
    Runnable,
    CloudDatabaseInterface.GetBatchDetailResponse,
    CloudDatabaseInterface.AcknowledgeFinishedBatchResponse {

    private MobileDeviceInterface device;
    private String batchGuid;
    private Response response;

    public UseCaseBatchFinishedRequest(MobileDeviceInterface device, String batchGuid, Response response){
        this.device = device;
        this.batchGuid = batchGuid;
        this.response = response;
    }

    public void run(){
        /// set the active batch data with the null
        device.getActiveBatch().setActiveBatch();

        //  stop the foreground service
        device.getActiveBatchForegroundServiceController().stopActiveBatchForegroundService();

        // stop the active batch channel
        device.getRealtimeActiveBatchMessages().detach();


        //set the active batch server node to complete (null)
        device.getCloudDatabase().updateActiveBatchServerNodeStatus(batchGuid);

        //get the batch detail
        device.getCloudDatabase().getBatchDetailRequest(batchGuid, this);

    }

    public void cloudDatabaseGetBatchDetailSuccess(BatchDetail batchDetail){
        //add this batch to the batch completed server node & acknowledge the finished batch
        device.getCloudDatabase().updateBatchCompletedServerNode(batchDetail);
        device.getCloudDatabase().acknowledgeFinishedBatchRequest(this);
    }

    public void cloudDatabaseGetBatchDetailFailure(){
        //acknowledge the finished batch
        device.getCloudDatabase().acknowledgeFinishedBatchRequest(this);
    }

    public void cloudDatabaseFinishedBatchAckComplete(){
        response.batchFinishedComplete();
    }

    public interface Response {
        void batchFinishedComplete();
    }
}
