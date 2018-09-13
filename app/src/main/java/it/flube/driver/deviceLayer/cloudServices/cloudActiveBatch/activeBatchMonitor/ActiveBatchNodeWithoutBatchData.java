/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class ActiveBatchNodeWithoutBatchData {

    private static final String TAG = "ActiveBatchNodeWithoutBatchData";


    private CloudActiveBatchInterface.ActiveBatchUpdated response;

    public void processNode(ActiveBatchNode nodeData, CloudActiveBatchInterface.ActiveBatchUpdated response){
        this.response = response;
        determineResponse(nodeData);
    }

    private void determineResponse(ActiveBatchNode nodeData){
        Timber.tag(TAG).d("         ...actionType -> " + nodeData.getActionType());
        Timber.tag(TAG).d("         ...actorType  -> " + nodeData.getActorType());

        switch (nodeData.getActionType()){
            case NO_BATCH:
                switch (nodeData.getActorType()){
                    case SERVER_ADMIN:
                        Timber.tag(TAG).d("         ...response -> noBatchByServerAdmin");
                        response.noBatchByServerAdmin();
                        break;
                    case MOBILE_USER:
                        Timber.tag(TAG).d("         ...response -> noBatchByMobileUser");
                        response.noBatchByMobileUser();
                        break;
                    case NOT_SPECIFIED:
                        Timber.tag(TAG).d("         ...response -> dataMismatchOnNode");
                        response.dataMismatchOnNode();
                        break;
                }
                break;

            case NOT_SPECIFIED:
                Timber.tag(TAG).d("         ...response -> noDataOnNode");
                response.noDataOnNode();
                break;

            case BATCH_REMOVED:
                if (nodeData.hasBatchGuid()) {
                    Timber.tag(TAG).d("         ...response -> batchRemoved");
                    response.batchRemoved(nodeData.getActorType(), nodeData.getBatchGuid());
                } else {
                    Timber.tag(TAG).w("         ...response -> dataMismatchOnNode");
                    response.dataMismatchOnNode();
                }
                break;

            case BATCH_WAITING_TO_FINISH:
                if (nodeData.hasBatchGuid()) {
                    Timber.tag(TAG).d("         ...response -> batchWaitingToFinish");
                    response.batchWaitingToFinish(nodeData.getActorType(), nodeData.getBatchGuid());
                } else {
                    Timber.tag(TAG).w("         ...response -> dataMismatchOnNode");
                    response.dataMismatchOnNode();
                }
                break;

            case BATCH_FINISHED:
                if (nodeData.hasBatchGuid()) {
                    Timber.tag(TAG).d("         ...response -> batchFinished");
                    response.batchFinished(nodeData.getActorType(), nodeData.getBatchGuid());
                } else {
                    Timber.tag(TAG).w("         ...response -> dataMismatchOnNode");
                    response.dataMismatchOnNode();
                }
                break;

            case BATCH_STARTED:
            case ORDER_STARTED:
            case STEP_STARTED:
                // should never see these action types WITHOUT batch data.
                Timber.tag(TAG).w("         ...this action type should always have batch data --> " + nodeData.getActionType().toString());
                Timber.tag(TAG).d("         ...response -> noBatch");
                response.dataMismatchOnNode();
                break;
        }
        Timber.tag(TAG).d("      ...processing COMPLETE!");
    }

}
