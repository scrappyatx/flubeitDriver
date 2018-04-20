/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.offerClaim;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import timber.log.Timber;

/**
 * Created on 3/28/2018
 * Project : Driver
 */
public class FirebaseDemoOfferClaim implements
        FirebaseDemoOffersRemove.Response,
        FirebaseScheduledBatchesAdd.Response {

    private static final String TAG = "FirebaseDemoOfferClaim";

    private DatabaseReference scheduledBatchesRef;
    private String batchGuid;
    CloudDemoOfferInterface.ClaimOfferResponse response;

    public FirebaseDemoOfferClaim(){

    }

    public void claimOfferRequest(DatabaseReference demoOffersRef, DatabaseReference scheduledBatchesRef,
                                  String batchGuid, CloudDemoOfferInterface.ClaimOfferResponse response){

        this.scheduledBatchesRef = scheduledBatchesRef;
        this.batchGuid = batchGuid;
        this.response = response;

        Timber.tag(TAG).d("demoOffersRef       = " + demoOffersRef.toString());
        Timber.tag(TAG).d("scheduledBatchesRef = " + scheduledBatchesRef.toString());
        Timber.tag(TAG).d("batchGuid           = " + batchGuid);

        // 1.  remove batchGuid from demoOffersRef
        // 2.  add batchGuid to scheduledBatchesRef

        new FirebaseDemoOffersRemove().removeDemoOfferFromOfferListRequest(demoOffersRef, batchGuid, this);

    }

    public void demoOfferRemoveComplete(){
        Timber.tag(TAG).d("   ...demoOfferRemove COMPLETE");
        new FirebaseScheduledBatchesAdd().addDemoBatchToScheduledBatchListRequest(scheduledBatchesRef, batchGuid, this );
    }

    public void scheduledBatchesAddComplete(){
        Timber.tag(TAG).d("   ...scheduledBatchesAdd COMPLETE");
        response.cloudClaimOfferRequestSuccess(batchGuid);
    }

}
