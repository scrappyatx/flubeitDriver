/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class UseCaseForfeitBatchRequest implements Runnable {
    private final MobileDeviceInterface device;
    private final String batchGuid;
    private final UseCaseForfeitBatchRequest.Response response;

    public UseCaseForfeitBatchRequest(MobileDeviceInterface device, String batchGuid, UseCaseForfeitBatchRequest.Response response){
        this.device = device;
        this.response = response;
        this.batchGuid = batchGuid;
    }

    public void run(){
        //device.getRealtimeBatchMessages().sendMsgForfeitBatch(batchGuid);
        response.forfeitBatchComplete(batchGuid);
    }

    public interface Response {
        void forfeitBatchComplete(String batchGuid);
    }
}
