/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class UseCaseForfeitBatchRequest implements
        Runnable,
        CloudDatabaseInterface.BatchForfeitResponse {

    private final MobileDeviceInterface device;
    private final String batchGuid;
    private final BatchDetail.BatchType batchType;
    private final UseCaseForfeitBatchRequest.Response response;

    public UseCaseForfeitBatchRequest(MobileDeviceInterface device, String batchGuid, BatchDetail.BatchType batchType, UseCaseForfeitBatchRequest.Response response){
        this.device = device;
        this.batchType = batchType;
        this.response = response;
        this.batchGuid = batchGuid;
    }

    public void run(){
        //device.getRealtimeBatchMessages().sendMsgForfeitBatch(batchGuid);
        device.getCloudDatabase().batchForfeitRequest(batchGuid, batchType, this);
    }

    public void cloudDatabaseBatchForfeitRequestComplete(){
        response.useCaseForfeitBatchRequestComplete(batchGuid);
    }

    public interface Response {
        void useCaseForfeitBatchRequestComplete(String batchGuid);
    }
}
