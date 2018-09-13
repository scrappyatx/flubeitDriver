/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class UseCaseClaimDemoOfferRequest implements
        Runnable,
        CloudDemoOfferInterface.ClaimOfferResponse {

    private static final String TAG = "UseCaseClaimDemoOfferRequest";

    private final Response response;
    private final String batchGuid;

    private final CloudDemoOfferInterface demoOffer;
    private final Driver driver;

    public UseCaseClaimDemoOfferRequest(MobileDeviceInterface device, String batchGuid, Response response){
        this.batchGuid = batchGuid;
        this.response = response;
        demoOffer = device.getCloudDemoOffer();
        driver = device.getCloudAuth().getDriver();
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        //remove this batch from demo offer list & add it to scheduled batch list
        demoOffer.claimOfferRequest(driver, batchGuid, this);
    }

    public void cloudClaimOfferRequestSuccess(String batchGuid){
        Timber.tag(TAG).d("...cloudClaimOfferRequest -> batchGuid = " + batchGuid);
        response.useCaseClaimOfferRequestSuccess(batchGuid);
    }

    public interface Response {
        void useCaseClaimOfferRequestSuccess(String batchGuid);

        void useCaseClaimOfferRequestFailure(String batchGuid);
    }

}
