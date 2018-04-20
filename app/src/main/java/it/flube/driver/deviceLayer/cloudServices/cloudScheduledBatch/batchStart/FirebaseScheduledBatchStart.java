/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchStart;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import timber.log.Timber;

/**
 * Created on 4/1/2018
 * Project : Driver
 */
public class FirebaseScheduledBatchStart implements
    FirebaseScheduledBatchesRemove.Response {
    private static final String TAG = "FirebaseActiveBatchStart";

    private CloudScheduledBatchInterface.StartScheduledBatchResponse response;

    public FirebaseScheduledBatchStart(){

    }

    public void startScheduledBatchRequest(DatabaseReference scheduledBatchesRef, String batchGuid, CloudScheduledBatchInterface.StartScheduledBatchResponse response){
        Timber.tag(TAG).d("scheduledBatchesRef = " + scheduledBatchesRef.toString());
        Timber.tag(TAG).d("batchGuid           = " + batchGuid);

        this.response = response;
        new FirebaseScheduledBatchesRemove().removeDemoBatchFromScheduledBatchListRequest(scheduledBatchesRef, batchGuid, this);
    }

    public void removeBatchFromScheduledBatchListComplete(){
        Timber.tag(TAG).d("   ...removeBatchFromScheduledBatchListComplete");
        response.cloudStartScheduledBatchComplete();
    }


}
