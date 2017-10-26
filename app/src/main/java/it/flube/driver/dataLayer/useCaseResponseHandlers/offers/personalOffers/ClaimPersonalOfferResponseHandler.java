/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers.personalOffers;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferFailureEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferSuccessEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferTimeoutEvent;
import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimOfferRequest;
import timber.log.Timber;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class ClaimPersonalOfferResponseHandler
    implements UseCaseClaimOfferRequest.Response {
        private final static String TAG = "ClaimPersonalOfferResponseHandler";

    // SUCCESS - Driver got offer
    public void useCaseClaimOfferRequestSuccess(String batchOID) {
        Timber.tag(TAG).d("user claim offer SUCCESS");
        EventBus.getDefault().postSticky(new ClaimPersonalOfferSuccessEvent(batchOID));
    }

    // FAILURE - Someone else got offer
    public void useCaseClaimOfferRequestFailure(String batchOID) {
        Timber.tag(TAG).d("user claim offer FAILURE");
        EventBus.getDefault().postSticky(new ClaimPersonalOfferFailureEvent(batchOID));
    }

    // TIMEOUT - Got no response
    public void useCaseClaimOfferRequestTimeoutNoResponse() {
        Timber.tag(TAG).w("time out waiting for response for claim offer request");
        EventBus.getDefault().postSticky(new ClaimPersonalOfferTimeoutEvent());
    }
}
