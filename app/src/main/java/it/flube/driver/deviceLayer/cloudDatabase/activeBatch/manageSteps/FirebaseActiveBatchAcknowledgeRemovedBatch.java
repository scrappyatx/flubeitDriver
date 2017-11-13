/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchSetData;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class FirebaseActiveBatchAcknowledgeRemovedBatch implements
    FirebaseActiveBatchSetData.Response {

    private CloudDatabaseInterface.AcknowledgeRemovedBatchResponse response;

    public void acknowledgeRemovedBatch(DatabaseReference activeBatchRef, CloudDatabaseInterface.AcknowledgeRemovedBatchResponse response){

        this.response = response;
        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, null, null, null,
                CloudDatabaseInterface.ActionType.NO_BATCH, CloudDatabaseInterface.ActorType.MOBILE_USER, this);
    }

    public void setDataComplete(){
        response.cloudDatabaseRemovedBatchAckComplete();
    }
}
