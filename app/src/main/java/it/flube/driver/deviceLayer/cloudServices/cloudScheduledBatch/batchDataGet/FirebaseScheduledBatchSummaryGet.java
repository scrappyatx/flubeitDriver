/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchDataGet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 3/30/2018
 * Project : Driver
 */
public class FirebaseScheduledBatchSummaryGet implements
        ValueEventListener {

    private static final String TAG = "FirebaseScheduledBatchSummaryGet";
    private static final String BATCH_SUMMARY = "batch";

    private CloudScheduledBatchInterface.GetBatchSummaryResponse response;

    public void getBatchSummary(DatabaseReference batchDataNodeRef, String batchGuid, CloudScheduledBatchInterface.GetBatchSummaryResponse response){
        this.response = response;
        Timber.tag(TAG).d("batchDataNode = " + batchDataNodeRef.toString());
        batchDataNodeRef.child(batchGuid).child(BATCH_SUMMARY).addListenerForSingleValueEvent(this);
    }


    public void onDataChange(DataSnapshot dataSnapshot){
        Timber.tag(TAG).d("onDataChange...");
        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists");
            try {
                Batch batch = dataSnapshot.getValue(Batch.class);
                Timber.tag(TAG).d("      ...got batch data node for batch guid : " + batch.getGuid());
                response.cloudGetScheduledBatchSummarySuccess(batch);
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...ERROR");
                Timber.tag(TAG).e(e);
                response.cloudGetScheduledBatchSummaryFailure();
            }
        } else {
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.cloudGetScheduledBatchSummaryFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).w("onCancelled --> error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetScheduledBatchSummaryFailure();
    }

}
