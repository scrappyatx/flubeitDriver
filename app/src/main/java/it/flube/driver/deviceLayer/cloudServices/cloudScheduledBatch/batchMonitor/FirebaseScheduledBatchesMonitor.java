/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.OffersInterface;
import timber.log.Timber;

/**
 * Created on 9/30/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchesMonitor {
    private static final String TAG = "FirebaseScheduledBatchesMonitor";

    private DatabaseReference scheduledBatchesRef;
    private DatabaseReference batchDataRef;
    private OffersInterface offersList;

    private FirebaseScheduledBatchEventListener scheduledBatchListener;
    private Boolean isListening;


    public FirebaseScheduledBatchesMonitor(DatabaseReference scheduledBatchesRef, DatabaseReference batchDataRef, OffersInterface offersList){
        Timber.tag(TAG).d("scheduledBatchesRef = " + scheduledBatchesRef);
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef);

        this.scheduledBatchesRef = scheduledBatchesRef;
        this.batchDataRef = batchDataRef;
        this.offersList = offersList;

        isListening = false;
    }

    ///
    ///  Start and Stop Listening for scheduled batches to be added to the list
    ///
    public void startListening(){
        if (!isListening) {
            scheduledBatchListener = new FirebaseScheduledBatchEventListener(batchDataRef, new ScheduledBatchesAvailableResponseHandler(offersList));
            scheduledBatchesRef.addValueEventListener(scheduledBatchListener);
            isListening = true;
            Timber.tag(TAG).d("START listening");
        } else {
            Timber.tag(TAG).d("called startListening when already listening...");
        }
    }

    public void stopListening(){
        if (isListening) {
            scheduledBatchesRef.removeEventListener(scheduledBatchListener);
            isListening = false;
            Timber.tag(TAG).d("STOP listening");
        } else {
            Timber.tag(TAG).d("called stopListening when not listening...");
        }
    }



}
