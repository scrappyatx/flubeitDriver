/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 10/18/2017
 * Project : Driver
 */

public class FirebaseActiveBatchStart implements
        FirebaseActiveBatchSetData.Response  {

    private static final String TAG = "FirebaseActiveBatchStart";

    private CloudActiveBatchInterface.StartActiveBatchResponse response;

    public void startBatchRequest(DatabaseReference activeBatchRef, String batchGuid, ActiveBatchManageInterface.ActorType actorType, CloudActiveBatchInterface.StartActiveBatchResponse response){
        this.response = response;

        /// set the data in the active batch node
        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef,
                batchGuid, 1, 1,
                ActiveBatchManageInterface.ActionType.BATCH_STARTED, actorType, this);

        Timber.tag(TAG).d("starting batch : batchGuid " + batchGuid);
    }

    public void setDataComplete(){
        response.cloudStartActiveBatchComplete();
        Timber.tag(TAG).d("starting batch : COMPLETE");
    }
}
