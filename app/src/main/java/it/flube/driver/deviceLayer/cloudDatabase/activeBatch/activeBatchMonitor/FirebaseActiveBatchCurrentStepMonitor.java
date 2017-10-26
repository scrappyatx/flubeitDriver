/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.ActiveBatchUpdatedResponseHandler;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseActiveBatchCurrentStepMonitor implements
        FirebaseActiveBatchCurrentStepListener.CurrentStepResponse {

    private static final String TAG = "FirebaseActiveBatchCurrentStepMonitor";
    private static final String ACTIVE_BATCH_CURRENT_STEPID_NODE = "currentStepIdSequence";

    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private FirebaseActiveBatchCurrentStepListener stepListener;

    private BatchDetail currentBatch;
    private ServiceOrder currentServiceOrder;

    private Boolean isListening;


    public FirebaseActiveBatchCurrentStepMonitor(DatabaseReference activeBatchRef, DatabaseReference batchDataRef,
                                                 BatchDetail batchDetail, ServiceOrder serviceOrder){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        this.activeBatchRef = activeBatchRef;
        this.batchDataRef = batchDataRef;
        this.currentBatch = batchDetail;
        this.currentServiceOrder = serviceOrder;

        isListening = false;
    }

    public void startListening(){
        if (!isListening) {
            stepListener = new FirebaseActiveBatchCurrentStepListener(batchDataRef, currentBatch.getBatchGuid(), currentServiceOrder.getGuid(), this);

            activeBatchRef.child(ACTIVE_BATCH_CURRENT_STEPID_NODE).addValueEventListener(stepListener);
            isListening = true;
            Timber.tag(TAG).d("STARTED listening");

        } else {
            Timber.tag(TAG).d("called startListening when already listening");
        }
    }

    public void stopListening(){
        if (isListening) {
            activeBatchRef.child(ACTIVE_BATCH_CURRENT_STEPID_NODE).removeEventListener(stepListener);
            isListening = false;

            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("called stopListening when not listening for an active batch...");
        }
    }

    public void currentStepSuccess(OrderStepInterface orderStep) {
        Timber.tag(TAG).d("current step found : step guid -> " + orderStep.getGuid());

        new ActiveBatchUpdatedResponseHandler()
                .cloudDatabaseActiveBatchUpdated(currentBatch, currentServiceOrder, orderStep);

        Timber.tag(TAG).d("sending ActiveBatchUpdated -> batch, serviceOrder, step");
    }

    public void currentStepFailure() {
        Timber.tag(TAG).d("no current step found");

        new ActiveBatchUpdatedResponseHandler()
                .cloudDatabaseNoActiveBatch();

        Timber.tag(TAG).d("sending ActiveBatchUpdated -> NO batch, NO service order, NO step");
    }
}
