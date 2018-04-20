/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import it.flube.libbatchdata.entities.claimOffer.ClaimOfferResponse;
import timber.log.Timber;

/**
 * Created on 3/20/2018
 * Project : Driver
 */

public class FirebaseClaimOfferResponseEventListener implements
        ValueEventListener {

    private static final String TAG = "FirebaseClaimOfferResponseEventListener";

    private Response response;


    public FirebaseClaimOfferResponseEventListener(Response response){
        this.response = response;
        Timber.tag(TAG).d("created");
    }

    public void onDataChange(DataSnapshot dataSnapshot){
        Timber.tag(TAG).d("dataSnapshot exists...");

        if (dataSnapshot.exists()){
            Timber.tag(TAG).d("   ...dataSnapshot exists");
            try {
                ClaimOfferResponse claimOfferResponse = dataSnapshot.getValue(ClaimOfferResponse.class);
                Timber.tag(TAG).d("   ...offerClaimed by clientId -> " + claimOfferResponse.getClientId());
                response.offerClaimed(claimOfferResponse);
            } catch (Exception e) {
                Timber.tag(TAG).w("         ...ERROR");
                Timber.tag(TAG).e(e);
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error --> " + databaseError.getCode() + " --> " + databaseError.getMessage());
    }

    public interface Response {
        void offerClaimed(ClaimOfferResponse claimOfferResponse);
    }

}
