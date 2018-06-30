/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.demoBatch;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.FirebaseActiveBatchSetData;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 6/9/2018
 * Project : Driver
 */
public class FirebaseDemoBatchStart implements
        FirebaseActiveBatchSetData.Response {

    private static final String TAG = "FirebaseDemoBatchStart";

    private String batchGuid;
    private CloudActiveBatchInterface.StartActiveBatchResponse response;

    public void startBatchRequest(DatabaseReference activeBatchRef, String batchGuid, ActiveBatchManageInterface.ActorType actorType, CloudActiveBatchInterface.StartActiveBatchResponse response){
        this.response = response;
        this.batchGuid = batchGuid;

        /// set the data in the active batch node
        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef,
                batchGuid, 1, 1,
                ActiveBatchManageInterface.ActionType.BATCH_STARTED, actorType, this);

        Timber.tag(TAG).d("starting batch : batchGuid " + batchGuid);
    }

    public void setDataComplete(){
        response.cloudStartActiveBatchSuccess(batchGuid);
        Timber.tag(TAG).d("starting batch : COMPLETE");
    }
}
