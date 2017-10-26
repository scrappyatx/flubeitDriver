/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderSave;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 10/17/2017
 * Project : Driver
 */

public class FirebaseBatchDetailSave implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseBatchDetailSave";
    private static final String BATCH_DATA_BATCH_DETAIL_NODE = "batchDetail";

    private SaveBatchDetailResponse response;

    public void saveBatchDetailRequest(DatabaseReference batchDataNodeRef, BatchDetail batchDetail, SaveBatchDetailResponse response){
        this.response = response;

        Timber.tag(TAG).d("batchDataNodeRef = " + batchDataNodeRef.toString());
        batchDataNodeRef.child(batchDetail.getBatchGuid()).child(BATCH_DATA_BATCH_DETAIL_NODE).child(batchDetail.getGuid()).setValue(batchDetail)
                .addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).w("   ...ERROR");
                Timber.tag(TAG).e(e);
            }
            Timber.tag(TAG).w("   ...FAILURE");
        }
        response.saveBatchDetailComplete();
    }

    public interface SaveBatchDetailResponse {
        void saveBatchDetailComplete();
    }
}
