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

public class UseCaseBatchRemovedRequest implements
    Runnable,
    CloudDatabaseInterface.AcknowledgeRemovedBatchResponse {

        private MobileDeviceInterface device;
        private String batchGuid;
        private UseCaseBatchRemovedRequest.Response response;

    public UseCaseBatchRemovedRequest(MobileDeviceInterface device, String batchGuid, UseCaseBatchRemovedRequest.Response response){
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

        //set the active batch server node to null
        device.getCloudDatabase().updateActiveBatchServerNodeStatus(batchGuid);

        //acknowledge the removed batch
        device.getCloudDatabase().acknowledgeRemovedBatchRequest(this);

    }

    public void cloudDatabaseRemovedBatchAckComplete(){
        response.batchRemovedComplete();
    }

    public interface Response {
        void batchRemovedComplete();
    }
}
