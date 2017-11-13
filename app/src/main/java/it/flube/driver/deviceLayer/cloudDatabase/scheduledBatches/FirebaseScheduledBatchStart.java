/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.scheduledBatches;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchSetData;
import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor.FirebaseActiveBatchMonitor;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseScheduledBatchStart implements
        FirebaseActiveBatchSetData.Response  {

    private static final String TAG = "FirebaseScheduledBatchStart";

    private CloudDatabaseInterface.StartScheduledBatchResponse response;

    public void startBatchRequest(DatabaseReference activeBatchRef, String batchGuid, CloudDatabaseInterface.ActorType actorType, CloudDatabaseInterface.StartScheduledBatchResponse response){
        this.response = response;

        /// set the data in the active batch node
        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef,
                batchGuid, 1, 1,
                CloudDatabaseInterface.ActionType.BATCH_STARTED, actorType, this);

        Timber.tag(TAG).d("starting batch : batchGuid " + batchGuid);
    }

    public void setDataComplete(){
        response.cloudDatabaseStartScheduledBatchComplete();
        Timber.tag(TAG).d("starting batch : COMPLETE");
    }
}
