/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 4/1/2018
 * Project : Driver
 */
public class FirebaseActiveBatchDetailGet implements ValueEventListener {
    private static final String TAG = "FirebaseActiveBatchDetailGet";
    private static final String BATCH_DETAIL = "batchDetail";

    private CloudActiveBatchInterface.GetBatchDetailResponse response;

    public FirebaseActiveBatchDetailGet(){}

    public void getBatchDetailRequest(DatabaseReference batchDataRef, String batchGuid, CloudActiveBatchInterface.GetBatchDetailResponse response){
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
                response.cloudGetActiveBatchDetailSuccess(batchDetail);
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...error while trying to get batch detail data");
                Timber.tag(TAG).e(e);
                response.cloudGetActiveBatchDetailFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.cloudGetActiveBatchDetailFailure();
        }
    }


    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error = " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetActiveBatchDetailFailure();
    }
}
