/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchForfeit.demoBatch;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.batchStart.FirebaseScheduledBatchesRemove;
import timber.log.Timber;

/**
 * Created on 3/29/2018
 * Project : Driver
 */
public class FirebaseDemoBatchForfeit implements
    FirebaseScheduledBatchesRemove.Response,
    FirebaseBatchDataDelete.Response {

    private static final String TAG = "FirebaseDemoBatchForfeit";

    private DatabaseReference scheduledBatchesRef;
    private DatabaseReference batchDataRef;
    private String batchGuid;
    private Response response;

    public void demoBatchForfeitRequest(DatabaseReference scheduledBatchesRef, DatabaseReference batchDataRef, String batchGuid, Response response){

        this.scheduledBatchesRef = scheduledBatchesRef;
        this.batchDataRef = batchDataRef;
        this.batchGuid = batchGuid;
        this.response = response;

        Timber.tag(TAG).d("scheduledBatchesRef    = " + scheduledBatchesRef.toString());
        Timber.tag(TAG).d("batchDataRef           = " + batchDataRef.toString());
        Timber.tag(TAG).d("batchGuid              = " + batchGuid);

        ///  1 - remove guid from scheduled batch list
        ///  2 - delete batch data from batch data node

        new FirebaseScheduledBatchesRemove().removeDemoBatchFromScheduledBatchListRequest(scheduledBatchesRef, batchGuid, this);
    }

    public void removeBatchFromScheduledBatchListComplete(){
        Timber.tag(TAG).d("   ...removeDemoBatchFromScheduledBatchListComplete");
        new FirebaseBatchDataDelete().deleteDemoBatchDataRequest(batchDataRef, batchGuid, this);
    }

    public void deleteDemoBatchDataComplete(){
        Timber.tag(TAG).d("   ...deleteDemoBatchDataComplete");
        response.demoBatchForfeitComplete(batchGuid);
    }

    public interface Response {
        void demoBatchForfeitComplete(String batchGuid);
    }

}
