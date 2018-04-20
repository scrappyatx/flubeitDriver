/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudOfferClaimInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.claimOffer.ClaimOfferResponse;
import timber.log.Timber;

/**
 * Created on 2/24/2018
 * Project : Driver
 */

public class FirebaseOfferClaimRequest implements
        FirebaseOfferClaimRequestWriteOffer.Response,
        FirebaseClaimOfferResponseMonitor.Response {

    private static final String TAG = "FirebaseOfferClaimRequest";

    private FirebaseClaimOfferResponseMonitor offerResponseMonitor;
    private String clientId;
    private String batchGuid;
    private CloudOfferClaimInterface.ClaimOfferResponse response;
    private DatabaseReference claimOfferResponseRef;

    public void claimOfferRequest(DatabaseReference claimOfferRequestRef, DatabaseReference claimOfferResponseRef,
                                  String clientId, String batchGuid, BatchDetail.BatchType batchType,
                                  CloudOfferClaimInterface.ClaimOfferResponse response){

        Timber.tag(TAG).d("claimOfferRequest START...");
        this.response = response;
        this.clientId = clientId;
        this.batchGuid = batchGuid;
        this.claimOfferResponseRef = claimOfferResponseRef;


        //write the offer request
        Timber.tag(TAG).d("...write the request");
        new FirebaseOfferClaimRequestWriteOffer().writeOfferRequest(claimOfferRequestRef, clientId, batchGuid, batchType, this);

        Timber.tag(TAG).d("...start listening for response");
        //start monitoring for a response
        offerResponseMonitor = new FirebaseClaimOfferResponseMonitor(claimOfferResponseRef, batchGuid,this);
        offerResponseMonitor.startListening();
    }

    /// callback from FirebaseClaimOfferResponseMonitor
    public void offerClaimed(ClaimOfferResponse claimOfferResponse){
        Timber.tag(TAG).d("   ...offerClaimed");

        //check to see if we got the offer, or if someone else did
        if (claimOfferResponse.getBatchGuid().equals(batchGuid)){
            Timber.tag(TAG).d("      ...batchGuid matches, now check who got it");
            if (claimOfferResponse.getClientId().equals(clientId)) {
                Timber.tag(TAG).d("         ...SUCCESS, we got it");
                response.cloudClaimOfferRequestSuccess(batchGuid);
            } else {
                Timber.tag(TAG).d("         ...FAILURE, some other schmo got it, clientId => " + claimOfferResponse.getClientId());
                response.cloudClaimOfferRequestFailure(batchGuid);
            }
        } else {
            //something wrong here, the batchGuid doesn't match
            Timber.tag(TAG).w("   ...batchGuid DOESN'T match, this should never happen");
            Timber.tag(TAG).w("      ...batchGuid SHOULD be -> " + batchGuid);
            Timber.tag(TAG).w("      ...batchGuid IS        -> " + claimOfferResponse.getBatchGuid());
            response.cloudClaimOfferRequestFailure(batchGuid);
        }
    }

    public void offerTimeout(){
        Timber.tag(TAG).d("   ...offerTimeout");
        response.cloudClaimOfferRequestTimeout(batchGuid);
    }

    /// callback from FirebaseOfferClaimRequestWriteOffer
    public void writeOfferComplete(){
        Timber.tag(TAG).d("   ...writeOfferComplete");
    }

}
