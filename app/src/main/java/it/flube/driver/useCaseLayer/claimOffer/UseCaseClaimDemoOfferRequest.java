/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class UseCaseClaimDemoOfferRequest implements
    Runnable,
    CloudDatabaseInterface.RemoveDemoOfferFromOfferListResponse,
    CloudDatabaseInterface.AddDemoBatchToScheduledBatchListResponse {

    private final UseCaseClaimDemoOfferRequest.Response response;
    private final String batchGuid;

    private final CloudDatabaseInterface cloudDb;

    public UseCaseClaimDemoOfferRequest(MobileDeviceInterface device, String batchGuid, UseCaseClaimDemoOfferRequest.Response response){
        this.batchGuid = batchGuid;
        this.response = response;
        cloudDb = device.getCloudDatabase();

    }

    public void run(){
        //remove this batch from demo offer list & add it to scheduled batch list
        cloudDb.removeDemoOfferFromOfferListRequest(batchGuid, this);
    }

    public void cloudDatabaseRemoveDemoOfferFromOfferListComplete(){
        cloudDb.addDemoBatchToScheduledBatchListRequest(batchGuid, this);
    }

    public void cloudDatabaseAddDemoBatchToScheduledBatchListComplete(){
        response.useCaseClaimDemoOfferRequestSuccess(batchGuid);
    }

    public interface Response {
        void useCaseClaimDemoOfferRequestSuccess(String offerGUID);

        void useCaseClaimDemoOfferRequestFailure(String offerGUID);
    }

}
