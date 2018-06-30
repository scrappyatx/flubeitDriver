/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchFinish;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.batchDetail.FirebaseBatchDetailSetStatus;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.FirebaseActiveBatchSetData;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 6/22/2018
 * Project : Driver
 */
public class FirebaseActiveBatchFinish implements
        FirebaseBatchDetailSetStatus.Response,
        FirebaseActiveBatchSetData.Response {

    private static final String TAG = "FirebaseActiveBatchFinish";

    private BatchDetail batchDetail;
    private CloudActiveBatchInterface.FinishActiveBatchResponse response;

    private ResponseCounter responseCounter;

    public void finishBatchRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef,
                                   ActiveBatchManageInterface.ActorType actorType,
                                   BatchDetail batchDetail, CloudActiveBatchInterface.FinishActiveBatchResponse response){

        Timber.tag(TAG).d("finishBatchRequest START...");

        this.response = response;

        responseCounter = new ResponseCounter(2);

        new FirebaseBatchDetailSetStatus().setBatchDetailStatusRequest(batchDataRef, batchDetail, BatchDetail.WorkStatus.COMPLETED_SUCCESS, this);

        new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), null, null,
                ActiveBatchManageInterface.ActionType.BATCH_FINISHED, actorType, this);

    }

    private void checkIfFinished(){
        responseCounter.onResponse();

        if (responseCounter.isFinished()){
            Timber.tag(TAG).d("      ...and we're done!");
            response.cloudActiveBatchFinished();
        } else {
            Timber.tag(TAG).d("      ...response " + responseCounter.getCount());
        }
    }

    public void setBatchDetailStatusComplete() {
        Timber.tag(TAG).d("   ...setBatchDetailStatusComplete");
        checkIfFinished();
    }

    public void setDataComplete() {
        Timber.tag(TAG).d("   ...setDataComplete");
        checkIfFinished();
    }

}
