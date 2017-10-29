/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderSetStatus;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class FireBatchDetailSetStatus implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseServiceOrderSetStatus";
    private static final String BATCH_DATA_BATCH_DETAIL_NODE = "batchDetail";
    private static final String STATUS_PROPERTY = "workStatus";

    private CloudDatabaseInterface.BatchDetailStatusUpdated response;


    public void setBatchDetailStatusRequest(DatabaseReference batchDataRef, BatchDetail batchDetail, BatchDetail.WorkStatus status,
                                            CloudDatabaseInterface.BatchDetailStatusUpdated response){
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(STATUS_PROPERTY, status);
        Timber.tag(TAG).d("...added key -> " + STATUS_PROPERTY + " value -> " + status.toString());

        batchDataRef.child(batchDetail.getBatchGuid()).child(BATCH_DATA_BATCH_DETAIL_NODE).updateChildren(data)
                .addOnCompleteListener(this);
    }


    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("onComplete...");
        if (task.isSuccessful()) {
            Timber.tag(TAG).d("   ...SUCCESS");
        } else {
            Timber.tag(TAG).w("   ...FAILURE");
            try {
                throw task.getException();
            } catch (Exception e) {
                Timber.tag(TAG).d("   ...ERROR");
                Timber.tag(TAG).e(e);
            }
        }
        response.cloudDatabaseBatchDetailStatusSetComplete();
    }

}
