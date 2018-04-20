/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.batch;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.libbatchdata.entities.batch.Batch;
import timber.log.Timber;

/**
 * Created on 10/9/2017
 * Project : Driver
 */


public class FirebaseBatchSummaryGet implements ValueEventListener {
    private static final String TAG = "FirebaseBatchSummaryGet";
    private static final String BATCH_SUMMARY = "batch";

    private GetBatchSummaryResponse response;

    public void getBatchSummary(DatabaseReference batchDataNodeRef, String batchGuid, GetBatchSummaryResponse response){
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
                response.getBatchSummarySuccess(batch);
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...ERROR");
                Timber.tag(TAG).e(e);
                response.getBatchSummaryFailure();
            }
        } else {
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.getBatchSummaryFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).w("onCancelled --> error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.getBatchSummaryFailure();
    }

    public interface GetBatchSummaryResponse {
        void getBatchSummarySuccess(Batch batch);

        void getBatchSummaryFailure();
    }
}

