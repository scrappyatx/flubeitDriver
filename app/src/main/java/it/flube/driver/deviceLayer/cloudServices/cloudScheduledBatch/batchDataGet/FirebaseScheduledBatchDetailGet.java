/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 3/30/2018
 * Project : Driver
 */
public class FirebaseScheduledBatchDetailGet implements ValueEventListener {
    private static final String TAG = "FirebaseScheduledBatchDetailGet";
    private static final String BATCH_DETAIL = "batchDetail";

    private CloudScheduledBatchInterface.GetBatchDetailResponse response;

    public FirebaseScheduledBatchDetailGet(){}

    public void getBatchDetailRequest(DatabaseReference batchDataRef, String batchGuid, CloudScheduledBatchInterface.GetBatchDetailResponse response){
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
                response.cloudGetScheduledBatchDetailSuccess(batchDetail);
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...error while trying to get batch detail data");
                Timber.tag(TAG).e(e);
                response.cloudGetScheduledBatchDetailFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.cloudGetScheduledBatchDetailFailure();
        }
    }


    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error = " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetScheduledBatchDetailFailure();
    }
}
