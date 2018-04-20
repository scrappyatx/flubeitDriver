/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudOfferClaimInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 3/19/2018
 * Project : Driver
 */

public class OfferClaimFirebaseWrapper implements
        CloudOfferClaimInterface {

    private static final String TAG = "OfferClaimFirebaseWrapper";

    private String offerClaimRequestNode;
    private String offerClaimResponseNode;

    public OfferClaimFirebaseWrapper(){
        Timber.tag(TAG).d("created");

        offerClaimRequestNode = "/userWriteable/claimOfferRequest";
        Timber.tag(TAG).d("offerClaimRequestNode = " + offerClaimRequestNode);

        offerClaimResponseNode = "/userReadable/claimOfferResponse";
        Timber.tag(TAG).d("offerClaimResponseNode = " + offerClaimResponseNode);

    }

    ////
    ////  CLAIM OFFER REQUEST
    ////
    public void claimOfferRequest(Driver driver, String batchGuid, BatchDetail.BatchType batchType, ClaimOfferResponse response){
        Timber.tag(TAG).d("claimOfferRequest START...");

        new FirebaseOfferClaimRequest().claimOfferRequest(FirebaseDatabase.getInstance().getReference(offerClaimRequestNode),
                FirebaseDatabase.getInstance().getReference(offerClaimResponseNode),
                driver.getClientId(), batchGuid, batchType,
                response);

    }


}
