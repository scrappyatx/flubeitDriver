/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.batchDetail;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/9/2017
 * Project : Driver
 */

public class FirebaseBatchDetailGet implements ValueEventListener {
    private static final String TAG = "FirebaseBatchDetailGet";
    private static final String BATCH_DETAIL = "batchDetail";

    private GetBatchDetailResponse response;

    public FirebaseBatchDetailGet(){}

    public void getBatchDetailRequest(DatabaseReference batchDataRef, String batchGuid, GetBatchDetailResponse response){
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
                response.getBatchDetailSuccess(batchDetail);
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...error while trying to get batch detail data");
                Timber.tag(TAG).e(e);
                response.getBatchDetailFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.getBatchDetailFailure();
        }
    }


    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error = " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.getBatchDetailFailure();
    }

    public interface GetBatchDetailResponse {
        void getBatchDetailSuccess(BatchDetail batchDetail);

        void getBatchDetailFailure();
    }

}
