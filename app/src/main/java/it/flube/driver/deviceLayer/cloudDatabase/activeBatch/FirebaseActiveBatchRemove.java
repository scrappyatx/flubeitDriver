/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 11/6/2017
 * Project : Driver
 */

public class FirebaseActiveBatchRemove implements
        FirebaseActiveBatchSetData.Response {

    private static final String TAG = "FirebaseActiveBatchRemove";

    private CloudDatabaseInterface.RemoveActiveBatchResponse response;

    public void removeBatchRequest(DatabaseReference activeBatchRef, CloudDatabaseInterface.ActorType actorType, CloudDatabaseInterface.RemoveActiveBatchResponse response){
        this.response = response;

        /// set the data in the active batch node
        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef,
                null, null, null,
                CloudDatabaseInterface.ActionType.BATCH_REMOVED, actorType, this);

        Timber.tag(TAG).d("removing active batch");
    }

    public void setDataComplete(){
        response.cloudDatabaseRemoveActiveBatchComplete();
        Timber.tag(TAG).d("removing active batch : COMPLETE");
    }
}
