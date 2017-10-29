/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.scheduledBatches;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor.FirebaseActiveBatchMonitor;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchStart implements OnCompleteListener<Void> {
    private static final String TAG = "FirebaseScheduledBatchStart";

    private static final String ACTIVE_BATCH_CURRENT_BATCH_NODE = "batch";
    private static final String ACTIVE_BATCH_CURRENT_SERVICE_ORDER_NODE = "currentServiceOrderSequence";
    private static final String ACTIVE_BATCH_CURRENT_STEPID_NODE = "currentStepIdSequence";

    private CloudDatabaseInterface.StartDemoBatchComplete response;
    private ResponseCounter responseCounter;

    public void startBatchRequest(DatabaseReference activeBatchRef, String batchGuid, CloudDatabaseInterface.StartDemoBatchComplete response){
        this.response = response;

       responseCounter = new ResponseCounter(3);

        //save batchGuid to activeBatch node
        activeBatchRef.child(ACTIVE_BATCH_CURRENT_BATCH_NODE).setValue(batchGuid)
                .addOnCompleteListener(this);

        //save "1" to current service order node
        activeBatchRef.child(ACTIVE_BATCH_CURRENT_SERVICE_ORDER_NODE).setValue(1)
                .addOnCompleteListener(this);

        //save "1" to current stepId node
        activeBatchRef.child(ACTIVE_BATCH_CURRENT_STEPID_NODE).setValue(1)
                .addOnCompleteListener(this);

        Timber.tag(TAG).d("starting demo batch : batchGuid " + batchGuid);
    }

    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");
        responseCounter.onResponse();
        Timber.tag(TAG).d("      ...responseCounter = " + Integer.toString(responseCounter.getCount()));
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

        if (responseCounter.isFinished()) {
            Timber.tag(TAG).d("COMPLETE");
            response.cloudDatabaseStartDemoBatchComplete();
        }
    }
}
