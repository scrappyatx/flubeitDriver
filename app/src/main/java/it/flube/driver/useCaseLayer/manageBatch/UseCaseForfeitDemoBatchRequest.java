/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 10/13/2017
 * Project : Driver
 */

public class UseCaseForfeitDemoBatchRequest implements
    Runnable,
    CloudDatabaseInterface.RemoveDemoBatchFromScheduledBatchListResponse,
    CloudDatabaseInterface.DeleteBatchDataResponse {

    private CloudDatabaseInterface cloudDb;
    private String batchGuid;
    private Response response;

    public UseCaseForfeitDemoBatchRequest(MobileDeviceInterface device, String batchGuid, Response response){
        this.response = response;
        this.batchGuid = batchGuid;
        cloudDb=device.getCloudDatabase();
    }
    public void run(){
        // 1. remove batch from scheduled batch list
        cloudDb.removeDemoBatchFromScheduledBatchListRequest(batchGuid, this);
    }

    public void cloudDatabaseRemoveDemoBatchFromScheduledBatchListComplete(){
        // 2. delete batch data for this batch
        cloudDb.deleteBatchDataRequest(batchGuid, this);
    }

    public void cloudDatabaseBatchDataDeleteComplete() {
        response.useCaseForfeitDemoBatchComplete(batchGuid);
    }

    public interface Response {
        void useCaseForfeitDemoBatchComplete(String batchGuid);
    }

}
