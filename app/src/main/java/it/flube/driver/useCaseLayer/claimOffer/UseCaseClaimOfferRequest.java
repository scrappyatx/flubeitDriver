/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 4/12/2018
 * Project : Driver
 */
public class UseCaseClaimOfferRequest implements
    Runnable,
    UseCaseClaimDemoOfferRequest.Response,
    UseCaseClaimPublicOfferRequest.Response,
    UseCaseClaimPersonalOfferRequest.Response {

    private static final String TAG = "UseCaseClaimOfferRequest";

    private final MobileDeviceInterface device;
    private final String batchGuid;
    private final OfferConstants.OfferType offerType;
    private final BatchDetail.BatchType batchType;
    private final Response response;


    public UseCaseClaimOfferRequest(MobileDeviceInterface device, String batchGuid, OfferConstants.OfferType offerType, BatchDetail.BatchType batchType, Response response){
        this.device = device;
        this.batchGuid = batchGuid;
        this.offerType = offerType;
        this.batchType = batchType;
        this.response = response;

    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        Timber.tag(TAG).d("   ...offerType -> " + offerType);

        switch (offerType){
            case DEMO:
                new UseCaseClaimDemoOfferRequest(device, batchGuid, this).run();
                break;
            case PUBLIC:
                new UseCaseClaimPublicOfferRequest(device, batchGuid, batchType, this).run();
                break;
            case PERSONAL:
                new UseCaseClaimPersonalOfferRequest(device, batchGuid, batchType, this).run();
                break;
            default:
                response.useCaseClaimOfferRequestFailure(batchGuid, offerType);
                break;
        }
    }

    /// responses
    public void useCaseClaimOfferRequestSuccess(String batchGuid){
        Timber.tag(TAG).d("   ...useCaseClaimOfferRequestSuccess");
        response.useCaseClaimOfferRequestSuccess(batchGuid, offerType);
    }

    public void useCaseClaimOfferRequestFailure(String batchGuid){
        Timber.tag(TAG).d("   ...useCaseClaimOfferRequestFailure");
        response.useCaseClaimOfferRequestFailure(batchGuid, offerType);
    }

    public void useCaseClaimOfferRequestTimeout(String batchGuid){
        Timber.tag(TAG).d("   ...useCaseClaimOfferRequestTimeout");
        response.useCaseClaimOfferRequestTimeout(batchGuid, offerType);
    }

    public interface Response {
        void useCaseClaimOfferRequestSuccess(String batchGuid, OfferConstants.OfferType offerType);

        void useCaseClaimOfferRequestFailure(String batchGuid, OfferConstants.OfferType offerType);

        void useCaseClaimOfferRequestTimeout(String batchGuid, OfferConstants.OfferType offerType);
    }
}
