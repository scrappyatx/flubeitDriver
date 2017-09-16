/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.claimOffer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.ClaimOfferResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.ClaimDemoOfferResponseHandler;
import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimDemoOfferRequest;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimOfferRequest;
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

    public void claimOfferRequest(Offer offer) {
        Timber.tag(TAG).d("claimOfferRequest STARTED");

        switch (offer.getOfferType()) {
            case MOBILE_DEMO:
                useCaseExecutor.execute(new UseCaseClaimDemoOfferRequest(device, offer, new ClaimDemoOfferResponseHandler()));
                break;
            case PRODUCTION:
            case PRODUCTION_TEST:
            default:
                useCaseExecutor.execute(new UseCaseClaimOfferRequest(device, offer, new ClaimOfferResponseHandler()));
                break;
        }
    }

    public void close(){
        useCaseExecutor.shutdown();
        device = null;
    }

}
