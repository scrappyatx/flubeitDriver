/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.ActiveBatchUpdatedResponseHandler;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.orderStep.StepId;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 8/22/2017
 * Project : Driver
 */

public class FirebaseActiveBatchMonitor {
    private static final String TAG = "FirebaseActiveBatchMonitor";

    private FirebaseActiveBatchCurrentBatchMonitor batchMonitor;

    public FirebaseActiveBatchMonitor(DatabaseReference activeBatchRef, DatabaseReference batchDataRef){
        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        batchMonitor = new FirebaseActiveBatchCurrentBatchMonitor(activeBatchRef,batchDataRef);
    }

    public void startListening(){
        batchMonitor.startListening();
        Timber.tag(TAG).d("START listening...");
    }

    public void stopListening(){
        batchMonitor.stopListening();
        Timber.tag(TAG).d("STOP listening...");
    }
}

