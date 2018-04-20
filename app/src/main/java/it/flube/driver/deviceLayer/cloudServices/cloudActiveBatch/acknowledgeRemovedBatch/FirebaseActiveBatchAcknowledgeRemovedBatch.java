/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.acknowledgeRemovedBatch;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.FirebaseActiveBatchSetData;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class FirebaseActiveBatchAcknowledgeRemovedBatch implements
    FirebaseActiveBatchSetData.Response {

    private CloudActiveBatchInterface.AcknowledgeRemovedBatchResponse response;

    public void acknowledgeRemovedBatch(DatabaseReference activeBatchRef, CloudActiveBatchInterface.AcknowledgeRemovedBatchResponse response){

        this.response = response;
        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, null, null, null,
                ActiveBatchManageInterface.ActionType.NO_BATCH, ActiveBatchManageInterface.ActorType.MOBILE_USER, this);
    }

    public void setDataComplete(){
        response.cloudActiveBatchRemovedBatchAckComplete();
    }
}
