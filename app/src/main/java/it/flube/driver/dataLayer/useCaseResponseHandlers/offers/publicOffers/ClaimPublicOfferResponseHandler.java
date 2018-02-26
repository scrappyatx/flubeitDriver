/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferFailureEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferSuccessEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferTimeoutEvent;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimOfferRequestDEPRECATED;
import timber.log.Timber;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class ClaimPublicOfferResponseHandler
     implements UseCaseClaimOfferRequestDEPRECATED.Response {
        private final static String TAG = "ClaimPublicOfferResponseHandler";

        // SUCCESS - Driver got offer
    public void useCaseClaimOfferRequestSuccess(String batchOID) {
        Timber.tag(TAG).d("user claim offer SUCCESS");
        EventBus.getDefault().postSticky(new ClaimPublicOfferSuccessEvent(batchOID));
    }

    // FAILURE - Someone else got offer
    public void useCaseClaimOfferRequestFailure(String batchOID) {
        Timber.tag(TAG).d("user claim offer FAILURE");
        EventBus.getDefault().postSticky(new ClaimPublicOfferFailureEvent(batchOID));
    }

    // TIMEOUT - Got no response
    public void useCaseClaimOfferRequestTimeoutNoResponse() {
        Timber.tag(TAG).w("time out waiting for response for claim offer request");
        EventBus.getDefault().postSticky(new ClaimPublicOfferTimeoutEvent());
    }
}
