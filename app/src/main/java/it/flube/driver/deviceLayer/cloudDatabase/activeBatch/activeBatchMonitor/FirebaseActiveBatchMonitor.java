/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor;

import com.google.firebase.database.DatabaseReference;

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

