/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailGet;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/11/2017
 * Project : Driver
 */

public class FirebaseActiveBatchCurrentBatchListener implements
        ValueEventListener,
        CloudDatabaseInterface.GetBatchDetailResponse {

    private final static String TAG = "FirebaseActiveBatchCurrentBatchListener";

    private DatabaseReference batchDataRef;
    private CurrentBatchResponse response;

    public FirebaseActiveBatchCurrentBatchListener(DatabaseReference batchDataRef, CurrentBatchResponse response){
        this.response = response;
        this.batchDataRef = batchDataRef;
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");

        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists!");
            try {
                String batchGuid = dataSnapshot.getValue(String.class);
                Timber.tag(TAG).d("      ...looking for offer data for guid : " + batchGuid);
                new FirebaseBatchDetailGet().getBatchDetailRequest(batchDataRef, batchGuid, this);
            } catch (Exception e) {
                Timber.tag(TAG).e(e);
                response.currentBatchFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
            response.currentBatchFailure();
        }
    }

    public void onCancelled(DatabaseError databaseError){
        Timber.tag(TAG).e("onCancelled - > firebase database read error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.currentBatchFailure();
    }

    public void cloudDatabaseGetBatchDetailSuccess(BatchDetail batchDetail) {
        Timber.tag(TAG).d("      ...got batchDetail!");
        response.currentBatchSuccess(batchDetail);
    }

    public void cloudDatabaseGetBatchDetailFailure() {
        Timber.tag(TAG).w("      ...could not get batchDetail");
        response.currentBatchFailure();
    }

    interface CurrentBatchResponse {
        void currentBatchSuccess(BatchDetail batchDetail);

        void currentBatchFailure();
    }
}
