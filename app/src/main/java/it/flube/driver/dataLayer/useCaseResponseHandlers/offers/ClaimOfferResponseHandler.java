/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCaseResponseHandlers.offers;

import org.greenrobot.eventbus.EventBus;


import it.flube.driver.useCaseLayer.claimOffer.UseCaseClaimOfferRequest;
import timber.log.Timber;

/**
 * Created on 7/23/2017
 * Project : Driver
 */

public class ClaimOfferResponseHandler implements UseCaseClaimOfferRequest.Response {
    private final static String TAG = "ClaimOfferResponseHandler";

    // SUCCESS - Driver got offer
    public void useCaseClaimOfferRequestSuccess(String batchOID) {
        Timber.tag(TAG).d("user claim offer SUCCESS");
        EventBus.getDefault().postSticky(new ClaimOfferResponseHandler.ClaimOfferSuccessEvent(batchOID));
    }

    public static class ClaimOfferSuccessEvent {
        private String batchOID;
        public ClaimOfferSuccessEvent(String batchOID){
            this.batchOID = batchOID;
        }
        public String getBatchOID(){ return batchOID; }
    }

    // FAILURE - Someone else got offer
    public void useCaseClaimOfferRequestFailure(String batchOID) {
        Timber.tag(TAG).d("user claim offer FAILURE");
        EventBus.getDefault().postSticky(new ClaimOfferResponseHandler.ClaimOfferFailureEvent(batchOID));
    }

    public static class ClaimOfferFailureEvent {
        private String batchOID;
        public ClaimOfferFailureEvent(String batchOID){
            this.batchOID = batchOID;
        }
        public String getBatchOID(){ return batchOID; }
    }

    // TIMEOUT - Got no response
    public void useCaseClaimOfferRequestTimeoutNoResponse() {
        Timber.tag(TAG).w("time out waiting for response for claim offer request");
        EventBus.getDefault().postSticky(new ClaimOfferResponseHandler.ClaimOfferTimeoutEvent());
    }

    public static class ClaimOfferTimeoutEvent {
        public ClaimOfferTimeoutEvent(){}
    }
}
