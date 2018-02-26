/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 7/23/2017
 * Project : Driver
 */

public class UseCaseOfferSelected implements
        Runnable,
        CloudDatabaseInterface.GetBatchDetailResponse {

    private final MobileDeviceInterface device;
    private final UseCaseOfferSelected.Response response;
    private final Batch offer;

    public UseCaseOfferSelected(MobileDeviceInterface device, Batch offer, UseCaseOfferSelected.Response response) {
        this.device = device;
        this.response = response;
        this.offer = offer;
    }

    public void run() {
        // get the batch detail that corresponse to this offer
        device.getCloudDatabase().getBatchDetailRequest(offer.getGuid(), this);
    }

    public void cloudDatabaseGetBatchDetailSuccess(BatchDetail batchDetail){
        response.offerSelectedSuccess(batchDetail);
    }

    public void cloudDatabaseGetBatchDetailFailure() {
        response.offerSelectedFailure();
    }

    public interface Response {
        void offerSelectedSuccess(BatchDetail offerDetail);

        void offerSelectedFailure();
    }
}
