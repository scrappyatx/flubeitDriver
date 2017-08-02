/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;

/**
 * Created on 7/22/2017
 * Project : Driver
 */

public class UseCaseClaimOfferRequest implements Runnable, RealtimeMessagingInterface.OfferChannel.ClaimOfferResponse {
    private final MobileDeviceInterface device;
    private final UseCaseClaimOfferRequest.Response response;
    private final String offerOID;
    private Boolean responseSeenForThisRequest;

    public UseCaseClaimOfferRequest(MobileDeviceInterface device, Offer offer, UseCaseClaimOfferRequest.Response response ){
        this.device = device;
        this.response = response;
        this.offerOID = offer.getOfferOID();
        responseSeenForThisRequest = false;
    }

    public void run(){
        device.getRealtimeOfferMessages().sendClaimOfferRequestMessage(offerOID, this);
    }

    public void receiveClaimOfferResponseMessage(String offerOID, String batchOID, String clientId) {
        responseSeenForThisRequest = true;

        Boolean sameDriver = device.getUser().getDriver().getClientId().equals(clientId);
        Boolean sameOffer = this.offerOID.equals(offerOID);

        if (sameOffer) {
            if (sameDriver) {
                response.useCaseClaimOfferRequestSuccess(offerOID);
            } else {
                response.useCaseClaimOfferRequestFailure(offerOID);
            }
        } else {
              // ignore this, we just happened to listen to the response message for another offer
              // it will either have it's own useCaseClaimRequest thread (if the user tried to claim it), OR
              // we can safely ignore it, because the user didn't try to claim it at all.
        }
    }

    public void claimOfferRequestTimeoutNoResponse() {
        if (!responseSeenForThisRequest) {
            response.useCaseClaimOfferRequestTimeoutNoResponse();
        }
    }

    public interface Response {
        void useCaseClaimOfferRequestSuccess(String offerOID);

        void useCaseClaimOfferRequestFailure(String offerOID);

        void useCaseClaimOfferRequestTimeoutNoResponse();

    }
}
