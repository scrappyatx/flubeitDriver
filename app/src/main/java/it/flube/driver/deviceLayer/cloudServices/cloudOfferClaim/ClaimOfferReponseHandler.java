/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim;

import it.flube.driver.modelLayer.interfaces.CloudOfferClaimInterface;

/**
 * Created on 3/20/2018
 * Project : Driver
 */

public class ClaimOfferReponseHandler implements
        CloudOfferClaimInterface.ClaimOfferResponse {

    public static final String TAG = "ClaimOfferReponseHandler";

    public void cloudClaimOfferRequestSuccess(String batchGuid){

    }

    public void cloudClaimOfferRequestFailure(String batchGuid){

    }

    public void cloudClaimOfferRequestTimeout(String batchGuid) {

    }

}
