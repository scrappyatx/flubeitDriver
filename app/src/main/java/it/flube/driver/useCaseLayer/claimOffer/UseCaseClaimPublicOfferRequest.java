/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudOfferClaimInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 4/12/2018
 * Project : Driver
 */
public class UseCaseClaimPublicOfferRequest implements
        Runnable,
        CloudOfferClaimInterface.ClaimOfferResponse {

    private static final String TAG = "UseCaseClaimPublicOfferRequest";

    private final UseCaseClaimPublicOfferRequest.Response response;
    private final String batchGuid;
    private final BatchDetail.BatchType batchType;
    private final CloudPublicOfferInterface publicOffer;
    private final Driver driver;

    public UseCaseClaimPublicOfferRequest(MobileDeviceInterface device, String batchGuid, BatchDetail.BatchType batchType, UseCaseClaimPublicOfferRequest.Response response){
        this.batchGuid = batchGuid;
        this.batchType = batchType;
        this.response = response;
        publicOffer = device.getCloudPublicOffer();
        driver = device.getUser().getDriver();
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        //remove this batch from demo offer list & add it to scheduled batch list
        publicOffer.claimOfferRequest(driver, batchGuid, batchType,this);
    }

    public void cloudClaimOfferRequestSuccess(String batchGuid){
        Timber.tag(TAG).d("...cloudClaimOfferRequest -> batchGuid = " + batchGuid);
        response.useCaseClaimOfferRequestSuccess(batchGuid);
    }

    public void cloudClaimOfferRequestFailure(String batchGuid){
        Timber.tag(TAG).d("...cloudClaimOfferRequestFailure -> batchGuid = " + batchGuid);
        response.useCaseClaimOfferRequestFailure(batchGuid);
    }

    public void cloudClaimOfferRequestTimeout(String batchGuid){
        Timber.tag(TAG).d("...cloudClaimOfferRequestTimeout -> batchGuid = " + batchGuid);
        response.useCaseClaimOfferRequestTimeout(batchGuid);
    }

    public interface Response {
        void useCaseClaimOfferRequestSuccess(String batchGuid);

        void useCaseClaimOfferRequestFailure(String batchGuid);

        void useCaseClaimOfferRequestTimeout(String batchGuid);
    }


}
