/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class UseCaseClaimDemoOfferRequest implements
    Runnable,
    CloudDatabaseInterface.SaveDemoBatchResponse,
    CloudDatabaseInterface.DeleteDemoOfferResponse {

    private final MobileDeviceInterface device;
    private final UseCaseClaimDemoOfferRequest.Response response;
    private final Offer offer;

    private final String demoOfferNode;
    private final String scheduledBatchNode;
    private final Driver driver;
    private final CloudDatabaseInterface cloudDb;

    public UseCaseClaimDemoOfferRequest(MobileDeviceInterface device, Offer offer, UseCaseClaimDemoOfferRequest.Response response){
        this.device = device;
        this.offer = offer;
        this.response = response;

        driver = device.getUser().getDriver();
        demoOfferNode = device.getAppRemoteConfig().getCloudDatabaseBaseNodeDemoOffers();
        scheduledBatchNode = device.getAppRemoteConfig().getCloudDatabaseBaseNodeScheduledBatches();
        cloudDb = device.getCloudDatabase();

    }

    public void run(){
            //save this offer as a scheduled batch
            cloudDb.saveDemoBatchRequest(scheduledBatchNode, driver, offer, this);
    }

    public void cloudDatabaseDemoBatchSaveComplete(){
        //delete this offer from demo offers
        cloudDb.deleteDemoOfferRequest(demoOfferNode, driver, offer, this);
    }

    public void cloudDatabaseDemoOfferDeleteComplete(){
        response.useCaseClaimDemoOfferRequestSuccess(offer.getGUID());
    }

    public interface Response {
        void useCaseClaimDemoOfferRequestSuccess(String offerGUID);

        void useCaseClaimDemoOfferRequestFailure(String offerGUID);
    }

}
