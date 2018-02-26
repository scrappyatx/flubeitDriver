/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 2/24/2018
 * Project : Driver
 */

public class UseCaseClaimOfferRequest implements
        Runnable,
        CloudDatabaseInterface.ClaimOfferResponse {

    private final Response response;
    private final String batchGuid;
    private final BatchDetail.BatchType batchType;

    private final CloudDatabaseInterface cloudDb;

    public UseCaseClaimOfferRequest(MobileDeviceInterface device, String batchGuid, Response response){
        this.batchGuid = batchGuid;
        this.response = response;
        this.batchType = BatchDetail.BatchType.PRODUCTION_TEST;
        cloudDb = device.getCloudDatabase();

    }

    public void run(){
        //make a request for this offer
        cloudDb.claimOfferRequest(batchGuid, batchType, this);
    }

    public void cloudDatabaseClaimOfferRequestComplete(){
        response.useCaseClaimOfferRequestComplete(batchGuid);
    }

    public interface Response {
        void useCaseClaimOfferRequestComplete(String batchGuid);
    }
}
