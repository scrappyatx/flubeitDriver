/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.offerDataGet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 3/29/2018
 * Project : Driver
 */
public class FirebasePersonalOfferDetailGet implements ValueEventListener {
    private static final String TAG = "FirebasePersonalOfferDetailGet";
    private static final String BATCH_DETAIL = "batchDetail";

    private CloudPersonalOfferInterface.GetBatchDetailResponse response;

    public FirebasePersonalOfferDetailGet(){}

    public void getBatchDetailRequest(DatabaseReference batchDataRef, String batchGuid, CloudPersonalOfferInterface.GetBatchDetailResponse response){
        this.response = response;
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        batchDataRef.child(batchGuid).child(BATCH_DETAIL).addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...batch detail data FOUND!");
            try {
                BatchDetail batchDetail = dataSnapshot.getValue(BatchDetail.class);
                Timber.tag(TAG).d("      ...got batch detail data for batch guid : " + batchDetail.getBatchGuid());
                response.cloudGetPersonalOfferBatchDetailSuccess(batchDetail);
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...error while trying to get batch detail data");
                Timber.tag(TAG).e(e);
                response.cloudGetPersonalOfferBatchDetailFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.cloudGetPersonalOfferBatchDetailFailure();
        }
    }


    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error = " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetPersonalOfferBatchDetailFailure();
    }
}
