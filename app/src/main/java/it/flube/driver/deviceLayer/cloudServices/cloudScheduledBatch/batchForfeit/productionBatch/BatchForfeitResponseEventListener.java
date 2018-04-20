/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim.FirebaseClaimOfferResponseEventListener;
import it.flube.libbatchdata.entities.claimOffer.ClaimOfferResponse;
import it.flube.libbatchdata.entities.forfeitBatch.ForfeitBatchResponse;
import timber.log.Timber;

/**
 * Created on 3/31/2018
 * Project : Driver
 */
public class BatchForfeitResponseEventListener implements
        ValueEventListener {

    private static final String TAG = "BatchForfeitResponseEventListener";

    private Response response;

    public BatchForfeitResponseEventListener(Response response){
        this.response = response;
    }

    public void onDataChange(DataSnapshot dataSnapshot){
        Timber.tag(TAG).d("onChildAdded...");

        if (dataSnapshot.exists()){
            Timber.tag(TAG).d("   ...dataSnapshot exists");
            try {
                ForfeitBatchResponse forfeitBatchResponse = dataSnapshot.getValue(ForfeitBatchResponse.class);
                Timber.tag(TAG).d("   ...approved  -> " + forfeitBatchResponse.getApproved());
                Timber.tag(TAG).d("   ...reason    -> " + forfeitBatchResponse.getReason());
                Timber.tag(TAG).d("   ...timestamp -> " + forfeitBatchResponse.getTimestamp());
                response.forfeitBatchResponseReceived(forfeitBatchResponse);
            } catch (Exception e) {
                Timber.tag(TAG).w("         ...ERROR");
                Timber.tag(TAG).e(e);
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist, do nothing");
        }
    }



    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error --> " + databaseError.getCode() + " --> " + databaseError.getMessage());
        //do nothing
    }

    public interface Response {
        void forfeitBatchResponseReceived(ForfeitBatchResponse forfeitBatchResponse);
    }

}
