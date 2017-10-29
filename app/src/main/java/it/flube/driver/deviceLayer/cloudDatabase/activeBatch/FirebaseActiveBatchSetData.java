/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class FirebaseActiveBatchSetData implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseActiveBatchSetData";

    private static final String ACTIVE_BATCH_CURRENT_BATCH_NODE = "batch";
    private static final String ACTIVE_BATCH_CURRENT_SERVICE_ORDER_NODE = "currentServiceOrderSequence";
    private static final String ACTIVE_BATCH_CURRENT_STEPID_NODE = "currentStepIdSequence";


    private CloudDatabaseInterface.ActiveBatchNodesUpdated response;

    public void setDataNullRequest(DatabaseReference activeBatchRef,
                                   CloudDatabaseInterface.ActiveBatchNodesUpdated response){

        this.response = response;
        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(ACTIVE_BATCH_CURRENT_BATCH_NODE, null);
        data.put(ACTIVE_BATCH_CURRENT_SERVICE_ORDER_NODE, null);
        data.put(ACTIVE_BATCH_CURRENT_STEPID_NODE, null);

        Timber.tag(TAG).d("setting active batch data to NULL...");
        activeBatchRef.setValue(data).addOnCompleteListener(this);
    }

    public void setDataRequest(DatabaseReference activeBatchRef, String batchGuid, Integer serviceOrderSequence, Integer stepSequence,
                               CloudDatabaseInterface.ActiveBatchNodesUpdated response){

        this.response = response;

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(ACTIVE_BATCH_CURRENT_STEPID_NODE, stepSequence);
        data.put(ACTIVE_BATCH_CURRENT_SERVICE_ORDER_NODE, serviceOrderSequence);
        data.put(ACTIVE_BATCH_CURRENT_BATCH_NODE, batchGuid);

        Timber.tag(TAG).d("setting active batch data...");
        Timber.tag(TAG).d("   batchGuid            --> " + batchGuid);
        Timber.tag(TAG).d("   serviceOrderSequence --> " + serviceOrderSequence);
        Timber.tag(TAG).d("   stepSequence         --> " + stepSequence);
        activeBatchRef.setValue(data).addOnCompleteListener(this);
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
        response.cloudDatabaseActiveBatchNodeSetComplete();
    }
}
