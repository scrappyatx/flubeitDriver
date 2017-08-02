/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.driver.modelLayer.entities.BatchCloudDB;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimOfferRequest;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class UseCaseForfeitBatchRequest implements Runnable {
    private final MobileDeviceInterface device;
    private final BatchCloudDB batch;
    private final UseCaseForfeitBatchRequest.Response response;

    public UseCaseForfeitBatchRequest(MobileDeviceInterface device, BatchCloudDB batch, UseCaseForfeitBatchRequest.Response response){
        this.device = device;
        this.response = response;
        this.batch = batch;
    }

    public void run(){
        device.getRealtimeBatchMessages().sendMsgForfeitBatch(batch.getOrderOID());
        response.forfeitBatchComplete(batch);
    }

    public interface Response {
        void forfeitBatchComplete(BatchCloudDB batch);
    }
}
