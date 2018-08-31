/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.productionBatch;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.productionBatch.BatchForfeitResponseEventListener;
import it.flube.libbatchdata.entities.forfeitBatch.ForfeitBatchResponse;
import it.flube.libbatchdata.entities.startBatch.StartBatchResponse;
import timber.log.Timber;

/**
 * Created on 6/9/2018
 * Project : Driver
 */
public class BatchStartResponseEventListener implements
        ValueEventListener {

    private static final String TAG = "BatchStartResponseEventListener";

    private Response response;

    public BatchStartResponseEventListener(Response response){
        this.response = response;
    }

    public void onDataChange(@NonNull DataSnapshot dataSnapshot){
        Timber.tag(TAG).d("onChildAdded...");

        if (dataSnapshot.exists()){
            Timber.tag(TAG).d("   ...dataSnapshot exists");
            try {
                StartBatchResponse startBatchResponse = dataSnapshot.getValue(StartBatchResponse.class);
                Timber.tag(TAG).d("   ...approved                 -> " + startBatchResponse.getApproved());
                Timber.tag(TAG).d("   ...reason                   -> " + startBatchResponse.getReason());
                Timber.tag(TAG).d("   ...timestamp                -> " + startBatchResponse.getTimestamp());
                Timber.tag(TAG).d("   ...driverProxyDialNumber    -> " + startBatchResponse.getDriverProxyDialNumber());
                Timber.tag(TAG).d("   ...driverProxyDisplayNumber -> " + startBatchResponse.getDriverProxyDisplayNumber());
                response.startBatchResponseReceived(startBatchResponse);
            } catch (Exception e) {
                Timber.tag(TAG).w("         ...ERROR");
                Timber.tag(TAG).e(e);
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist, do nothing");
        }
    }



    public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error --> " + databaseError.getCode() + " --> " + databaseError.getMessage());
        //do nothing
    }

    public interface Response {
        void startBatchResponseReceived(StartBatchResponse startBatchResponse);
    }

}
