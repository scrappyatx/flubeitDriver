/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.ClaimDemoOfferResponseHandler;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimDemoOfferRequest;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 7/22/2017
 * Project : Driver
 */

public class OfferClaimController {
    private final String TAG = "OfferClaimController";
    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;

    public OfferClaimController() {
        useCaseExecutor = Executors.newSingleThreadExecutor();
        device = AndroidDevice.getInstance();
    }

    public void claimOfferRequest(BatchDetail batchDetail) {
        Timber.tag(TAG).d("claimOfferRequest STARTED");


        switch (batchDetail.getBatchType()) {
            case MOBILE_DEMO:
                useCaseExecutor.execute(new UseCaseClaimDemoOfferRequest(device, batchDetail.getBatchGuid(), new ClaimDemoOfferResponseHandler()));
                break;
            case PRODUCTION:
            case PRODUCTION_TEST:
            default:
                //useCaseExecutor.execute(new UseCaseClaimOfferRequest(device, batchDetail.getBatchGuid(), new ClaimPublicOfferResponseHandler()));
                break;
        }


    }

    public void close(){
        useCaseExecutor.shutdown();
        device = null;
    }

}
