/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.ActiveBatchUpdatedResponseHandler;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseActiveBatchCurrentServiceOrderMonitor implements
        FirebaseActiveBatchCurrentServiceOrderListener.CurrentServiceOrderResponse {

    private static final String TAG = "FirebaseActiveBatchCurrentBatchMonitor";
    private static final String ACTIVE_BATCH_CURRENT_SERVICE_ORDER_NODE = "currentServiceOrderSequence";

    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private FirebaseActiveBatchCurrentServiceOrderListener serviceOrderListener;

    private BatchDetail currentBatch;

    private Boolean isListening;
    private Boolean isMonitoringStep;

    private FirebaseActiveBatchCurrentStepMonitor stepMonitor;

    public FirebaseActiveBatchCurrentServiceOrderMonitor(DatabaseReference activeBatchRef, DatabaseReference batchDataRef, BatchDetail currentBatch){
        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        this.activeBatchRef = activeBatchRef;
        this.batchDataRef = batchDataRef;
        this.currentBatch = currentBatch;

        isListening = false;
        isMonitoringStep = false;
    }

    public void startListening(){
        if (!isListening) {
            serviceOrderListener = new FirebaseActiveBatchCurrentServiceOrderListener(batchDataRef, currentBatch.getBatchGuid() , this);

            activeBatchRef.child(ACTIVE_BATCH_CURRENT_SERVICE_ORDER_NODE).addValueEventListener(serviceOrderListener);
            isListening = true;
            Timber.tag(TAG).d("STARTED listening");

        } else {
            Timber.tag(TAG).d("called startListening when already listening");
        }
        stopMonitoringStep();
    }

    public void stopListening(){
        if (isListening) {
            activeBatchRef.child(ACTIVE_BATCH_CURRENT_SERVICE_ORDER_NODE).removeEventListener(serviceOrderListener);
            isListening = false;

            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("called stopListening when not listening for an active batch...");
        }
        stopMonitoringStep();
    }


    public void currentServiceOrderSuccess(ServiceOrder serviceOrder) {
        Timber.tag(TAG).d("current service order found : serviceOrder guid -> " + serviceOrder.getGuid());
        startMonitoringStep(currentBatch, serviceOrder);
    }

    public void currentServiceOrderFailure() {
        Timber.tag(TAG).d("no current service order found");
        stopMonitoringStep();

        new ActiveBatchUpdatedResponseHandler()
                .cloudDatabaseNoActiveBatch();

        Timber.tag(TAG).d("sending ActiveBatchUpdated -> NO batch, NO service order, NO step");
    }

    private void startMonitoringStep(BatchDetail batchDetail, ServiceOrder serviceOrder) {
        stepMonitor = new FirebaseActiveBatchCurrentStepMonitor(activeBatchRef, batchDataRef, batchDetail, serviceOrder);
        stepMonitor.startListening();
        isMonitoringStep = true;
        Timber.tag(TAG).d("STARTED monitoring service order");
    }

    private void stopMonitoringStep(){
        if (isMonitoringStep){
            Timber.tag(TAG).d("STOPPED monitoring service order");
            stepMonitor.stopListening();
            isMonitoringStep = false;
        }
    }
}
