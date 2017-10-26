/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.scheduledBatches;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ScheduledBatchesAvailableResponseHandler;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 9/30/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchesMonitor {
    private static final String TAG = "FirebaseScheduledBatchesMonitor";

    private DatabaseReference scheduledBatchesRef;
    private DatabaseReference batchDataRef;

    private FirebaseScheduledBatchEventListener scheduledBatchListener;
    private Boolean isListening;


    public FirebaseScheduledBatchesMonitor(DatabaseReference scheduledBatchesRef, DatabaseReference batchDataRef){
        Timber.tag(TAG).d("scheduledBatchesRef = " + scheduledBatchesRef);
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef);

        this.scheduledBatchesRef = scheduledBatchesRef;
        this.batchDataRef = batchDataRef;
        isListening = false;
    }

    ///
    ///  Start and Stop Listening for scheduled batches to be added to the list
    ///
    public void startListening(){
        if (!isListening) {
            scheduledBatchListener = new FirebaseScheduledBatchEventListener(batchDataRef, new ScheduledBatchesAvailableResponseHandler());
            scheduledBatchesRef.addValueEventListener(scheduledBatchListener);
            isListening = true;
            Timber.tag(TAG).d("START listening");
        } else {
            Timber.tag(TAG).w("called startListening when already listening...");
        }
    }

    public void stopListening(){
        if (isListening) {
            scheduledBatchesRef.removeEventListener(scheduledBatchListener);
            isListening = false;
            Timber.tag(TAG).w("STOP listening");
        } else {
            Timber.tag(TAG).w("called stopListening when not listening...");
        }
    }

    ///
    ///  Add a scheduled batch to the scheduled batch list
    ///
    public void addDemoBatchToScheduledBatchListRequest(String batchGuid, CloudDatabaseInterface.AddDemoBatchToScheduledBatchListResponse response) {
        // save the batch
        scheduledBatchesRef.child(batchGuid).setValue(true).addOnCompleteListener(new AddDemoBatchToScheduledBatchListCompleteListener(response));
        Timber.tag(TAG).d("adding batch to scheduled batch list --> batch Guid : " + batchGuid);
    }

    private class AddDemoBatchToScheduledBatchListCompleteListener implements OnCompleteListener<Void> {

        private CloudDatabaseInterface.AddDemoBatchToScheduledBatchListResponse response;


        public AddDemoBatchToScheduledBatchListCompleteListener(CloudDatabaseInterface.AddDemoBatchToScheduledBatchListResponse response){
            this.response = response;
        }
        public void onComplete(@NonNull Task<Void> task) {

            if (task.isSuccessful()) {
                Timber.tag(TAG).d("addDemoBatchToScheduledBatchList --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("addDemoBatchToScheduledBatchList --> FAILURE");
            }

            response.cloudDatabaseAddDemoBatchToScheduledBatchListComplete();
        }
    }

    ///
    ///  Remove a scheduled batch from the scheduled batch list
    ///
    public void removeDemoBatchFromScheduledBatchListRequest(String batchGuid, CloudDatabaseInterface.RemoveDemoBatchFromScheduledBatchListResponse response) {
        scheduledBatchesRef.child(batchGuid).setValue(null).addOnCompleteListener(new RemoveDemoOfferFromOfferListCompleteListener(response));
        Timber.tag(TAG).d("deleting DEMO OFFER --> batchGuid : " + batchGuid);
    }

    private class RemoveDemoOfferFromOfferListCompleteListener implements OnCompleteListener<Void> {
        private CloudDatabaseInterface.RemoveDemoBatchFromScheduledBatchListResponse response;

        public RemoveDemoOfferFromOfferListCompleteListener(CloudDatabaseInterface.RemoveDemoBatchFromScheduledBatchListResponse response) {
            this.response = response;
        }

        public void onComplete(@NonNull Task<Void> task) {

            if (task.isSuccessful()) {
                Timber.tag(TAG).d("deleteDemoOfferRequest --> SUCCESS");
            } else {
                try {
                    throw task.getException();
                }
                catch (Exception e) {
                    Timber.tag(TAG).e(e);
                }
                Timber.tag(TAG).w("deleteDemoOfferRequest --> FAILURE");
            }

            response.cloudDatabaseRemoveDemoBatchFromScheduledBatchListComplete();
        }
    }



}
