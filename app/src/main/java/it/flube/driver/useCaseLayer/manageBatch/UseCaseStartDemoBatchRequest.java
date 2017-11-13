/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.DemoBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 8/13/2017
 * Project : Driver
 */

public class UseCaseStartDemoBatchRequest implements
        Runnable,
        CloudDatabaseInterface.RemoveDemoBatchFromScheduledBatchListResponse,
        CloudDatabaseInterface.StartScheduledBatchResponse {

    private final CloudDatabaseInterface cloudDb;
    private final String batchGuid;
    private final UseCaseStartDemoBatchRequest.Response response;

    private Batch demoBatch;

    public UseCaseStartDemoBatchRequest(MobileDeviceInterface device, String batchGuid, UseCaseStartDemoBatchRequest.Response response){
        cloudDb = device.getCloudDatabase();
        this.batchGuid = batchGuid;
        this.response = response;
    }

    public void run(){
        //Step 1: remove batchGuid from scheduled batches list
        cloudDb.removeDemoBatchFromScheduledBatchListRequest(batchGuid, this);
    }

    public void cloudDatabaseRemoveDemoBatchFromScheduledBatchListComplete() {
        //Step 2: start the demo batch
        ///
        ///     "Starting a batch" is simply writing values to the "ActiveBatch Nodes" in the cloud db.
        ///
        ///     Since a batch can be started by the mobile device, OR by the back-end server, the RESPONSE to a change
        ///     in the ActiveBatch nodes is handled by another use case
        ///
        cloudDb.startScheduledBatchRequest(batchGuid, CloudDatabaseInterface.ActorType.MOBILE_USER,this);
        //cloudDb.setActiveBatchNodesRequest(batchGuid, 1, 1, CloudDatabaseInterface.ActionType.BATCH_STARTED, this);
    }

    public void cloudDatabaseStartScheduledBatchComplete() {
        //we are done
        response.startDemoBatchComplete(batchGuid);
    }

    public void cloudDatabaseActiveBatchNodeSetComplete(){
        //we are done
        response.startDemoBatchComplete(batchGuid);
    }


    public interface Response {
        void startDemoBatchComplete(String batchGuid);
    }

}
