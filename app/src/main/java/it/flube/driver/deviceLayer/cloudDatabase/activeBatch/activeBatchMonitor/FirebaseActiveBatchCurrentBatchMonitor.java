/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.dataLayer.useCaseResponseHandlers.activeBatch.ActiveBatchUpdatedResponseHandler;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseActiveBatchCurrentBatchMonitor implements
    FirebaseActiveBatchCurrentBatchListener.CurrentBatchResponse {

    private static final String TAG = "FirebaseActiveBatchCurrentBatchMonitor";
    private static final String ACTIVE_BATCH_CURRENT_BATCH_NODE = "batch";

    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private FirebaseActiveBatchCurrentBatchListener batchListener;

    private Boolean isListening;
    private Boolean isMonitoringServiceOrder;

    private FirebaseActiveBatchCurrentServiceOrderMonitor serviceOrderMonitor;


    public FirebaseActiveBatchCurrentBatchMonitor(DatabaseReference activeBatchRef, DatabaseReference batchDataRef){
        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        this.activeBatchRef = activeBatchRef;
        this.batchDataRef = batchDataRef;

        isListening = false;
        isMonitoringServiceOrder = false;
    }

    public void startListening(){
        if (!isListening) {

            batchListener = new FirebaseActiveBatchCurrentBatchListener(batchDataRef, this);

            activeBatchRef.child(ACTIVE_BATCH_CURRENT_BATCH_NODE).addValueEventListener(batchListener);
            isListening = true;
            Timber.tag(TAG).d("STARTED listening");

        } else {
            Timber.tag(TAG).d("called startListening when already listening");
        }
        stopMonitoringServiceOrder();
    }

    public void stopListening(){
        if (isListening) {
            activeBatchRef.child(ACTIVE_BATCH_CURRENT_BATCH_NODE).removeEventListener(batchListener);
            isListening = false;

            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("called stopListening when not listening for an active batch...");
        }
        stopMonitoringServiceOrder();
    }

    public void currentBatchSuccess(BatchDetail batchDetail) {
        Timber.tag(TAG).d("current batch found : batchGuid -> " + batchDetail.getBatchGuid());
        startMonitoringServiceOrder(batchDetail);
    }

    public void currentBatchFailure() {
        Timber.tag(TAG).d("no current batch found");
        stopMonitoringServiceOrder();

        new ActiveBatchUpdatedResponseHandler().noBatch();

        Timber.tag(TAG).d("sending ActiveBatchUpdated -> NO batch, NO service order, NO step");
    }

    private void startMonitoringServiceOrder(BatchDetail batchDetail) {
        serviceOrderMonitor = new FirebaseActiveBatchCurrentServiceOrderMonitor(activeBatchRef, batchDataRef, batchDetail);
        serviceOrderMonitor.startListening();
        isMonitoringServiceOrder = true;
        Timber.tag(TAG).d("STARTED monitoring service order");
    }

    private void stopMonitoringServiceOrder(){
        if (isMonitoringServiceOrder){
            Timber.tag(TAG).d("STOPPED monitoring service order");
            serviceOrderMonitor.stopListening();
        }
    }
}
