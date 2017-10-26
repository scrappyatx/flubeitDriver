/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferFailureEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferSuccessEvent;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimDemoOfferRequest;
import timber.log.Timber;

/**
 * Created on 9/13/2017
 * Project : Driver
 */

public class ClaimDemoOfferResponseHandler implements
        UseCaseClaimDemoOfferRequest.Response {

    private final static String TAG = "ClaimDemoOfferResponseHandler";

    // SUCCESS - Driver got offer
    public void useCaseClaimDemoOfferRequestSuccess(String offerGUID) {
        Timber.tag(TAG).d("user claim demo offer SUCCESS");
        EventBus.getDefault().postSticky(new ClaimDemoOfferSuccessEvent(offerGUID));
    }

    // FAILURE - Someone else got offer
    public void useCaseClaimDemoOfferRequestFailure(String offerGUID) {
        Timber.tag(TAG).d("user claim demo offer FAILURE");
        EventBus.getDefault().postSticky(new ClaimDemoOfferFailureEvent(offerGUID));
    }

}
