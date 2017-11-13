/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailGet;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class ActiveBatchNodeWithoutBatchData {

    private static final String TAG = "ActiveBatchNodeWithoutBatchData";


    private CloudDatabaseInterface.ActiveBatchUpdated response;

    public void processNode(ActiveBatchNode nodeData, CloudDatabaseInterface.ActiveBatchUpdated response){
        this.response = response;
        determineResponse(nodeData);
    }

    private void determineResponse(ActiveBatchNode nodeData){
        Timber.tag(TAG).d("         ...actionType -> " + nodeData.getActionType());
        Timber.tag(TAG).d("         ...actorType  -> " + nodeData.getActorType());

        switch (nodeData.getActionType()){
            case NO_BATCH:
            case NOT_SPECIFIED:
                Timber.tag(TAG).d("         ...response -> noBatch");
                response.noBatch();
                break;

            case BATCH_REMOVED:
                if (nodeData.hasBatchGuid()) {
                    Timber.tag(TAG).d("         ...response -> batchRemoved");
                    response.batchRemoved(nodeData.getActorType(), nodeData.getBatchGuid());
                } else {
                    Timber.tag(TAG).w("         ...batchRemoved, but nodeData is missing batchGuid");
                    response.noBatch();
                }


            case BATCH_FINISHED:
                if (nodeData.hasBatchGuid()) {
                    Timber.tag(TAG).d("         ...response -> batchFinished");
                    response.batchFinished(nodeData.getActorType(), nodeData.getBatchGuid());
                } else {
                    Timber.tag(TAG).w("         ...batchFinished, but nodeData is missing batchGuid");
                    response.noBatch();
                }

            case BATCH_STARTED:
            case ORDER_STARTED:
            case STEP_STARTED:
                // should never see these action types WITHOUT batch data.
                Timber.tag(TAG).w("         ...this action type should always have batch data --> " + nodeData.getActionType().toString());
                Timber.tag(TAG).d("         ...response -> noBatch");
                response.noBatch();
                break;
        }
        Timber.tag(TAG).d("      ...processing COMPLETE!");
    }

}
