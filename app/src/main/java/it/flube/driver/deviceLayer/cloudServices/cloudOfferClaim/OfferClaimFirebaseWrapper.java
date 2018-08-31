/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim;

import com.google.firebase.database.FirebaseDatabase;

import it.flube.driver.deviceLayer.cloudServices.firebaseInitialization.FirebaseDbInitialization;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudOfferClaimInterface;
import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim.OfferClaimFirebaseConstants.OFFER_CLAIM_REQUEST_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim.OfferClaimFirebaseConstants.OFFER_CLAIM_RESPONSE_NODE;

/**
 * Created on 3/19/2018
 * Project : Driver
 */

public class OfferClaimFirebaseWrapper implements
        CloudOfferClaimInterface {

    private static final String TAG = "OfferClaimFirebaseWrapper";

    private final String driverDb;
    private String offerClaimRequestNode;
    private String offerClaimResponseNode;

    public OfferClaimFirebaseWrapper(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
        Timber.tag(TAG).d("created");
        driverDb = FirebaseDbInitialization.getFirebaseDriverDb(targetEnvironment);

        offerClaimRequestNode = OFFER_CLAIM_REQUEST_NODE;
        Timber.tag(TAG).d("offerClaimRequestNode = " + offerClaimRequestNode);

        offerClaimResponseNode = OFFER_CLAIM_RESPONSE_NODE;
        Timber.tag(TAG).d("offerClaimResponseNode = " + offerClaimResponseNode);

    }

    ////
    ////  CLAIM OFFER REQUEST
    ////
    public void claimOfferRequest(Driver driver, String batchGuid, BatchDetail.BatchType batchType, ClaimOfferResponse response){
        Timber.tag(TAG).d("claimOfferRequest START...");

        new FirebaseOfferClaimRequest().claimOfferRequest(FirebaseDatabase.getInstance(driverDb).getReference(offerClaimRequestNode),
                FirebaseDatabase.getInstance(driverDb).getReference(offerClaimResponseNode),
                driver.getClientId(), batchGuid, batchType,
                response);

    }


}
