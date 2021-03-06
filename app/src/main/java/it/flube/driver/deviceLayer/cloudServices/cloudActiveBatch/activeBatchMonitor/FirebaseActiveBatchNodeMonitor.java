/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor;

import com.google.firebase.database.DatabaseReference;

import timber.log.Timber;

/**
 * Created on 11/5/2017
 * Project : Driver
 */

public class FirebaseActiveBatchNodeMonitor {

    private static final String TAG = "FirebaseActiveBatchNodeMonitor";


    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private FirebaseActiveBatchNodeListener nodeListener;

    private Boolean isListening;

    public FirebaseActiveBatchNodeMonitor(DatabaseReference activeBatchRef, DatabaseReference batchDataRef){
            Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
            Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
            this.activeBatchRef = activeBatchRef;
            this.batchDataRef = batchDataRef;

            isListening = false;
        }

    public void startListening(){
        if (!isListening) {

            nodeListener = new FirebaseActiveBatchNodeListener(batchDataRef, new ActiveBatchUpdatedResponseHandler());

            activeBatchRef.addValueEventListener(nodeListener);
            isListening = true;
            Timber.tag(TAG).d("STARTED listening");

        } else {
            Timber.tag(TAG).d("called startListening when already listening");
        }
    }

    public void stopListening(){
        if (isListening) {
            activeBatchRef.removeEventListener(nodeListener);
            isListening = false;

            Timber.tag(TAG).d("STOPPED listening");
        } else {
            Timber.tag(TAG).d("called stopListening when not listening for an active batch...");
        }
    }

}
