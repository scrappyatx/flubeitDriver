/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.completedBatches;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 10/30/2017
 * Project : Driver
 */

public class FirebaseCompletedBatchesServerNode implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseCompletedBatchesServerNode";

    private static final String CLIENT_ID_PROPERTY = "clientId";
    private static final String BATCH_TYPE_PROPERTY = "batchType";
    private static final String BATCH_COMPLETED_TIME_PROPERTY = "batchCompletedTimestamp";

    private static final String COMPLETED_BATCH_SERVER_NOTIFICATION_NODE = "userWriteable/completedBatches";

    public void setCompletedBatchRequest(DatabaseReference completedBatchRef, Driver driver, BatchDetail batchDetail){

        Timber.tag(TAG).d("completedBatchRef = " + completedBatchRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(CLIENT_ID_PROPERTY, driver.getClientId());
        data.put(BATCH_TYPE_PROPERTY, batchDetail.getBatchType());
        data.put(BATCH_COMPLETED_TIME_PROPERTY, ServerValue.TIMESTAMP);

        Timber.tag(TAG).d("setting completed batch data...");
        Timber.tag(TAG).d("   clientId    --> " + driver.getClientId());
        Timber.tag(TAG).d("   batchType   --> " + batchDetail.getBatchType().toString());
        Timber.tag(TAG).d("   finish timestamp");

        completedBatchRef.child(COMPLETED_BATCH_SERVER_NOTIFICATION_NODE).child(batchDetail.getBatchGuid()).setValue(data).addOnCompleteListener(this);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
    }
}
