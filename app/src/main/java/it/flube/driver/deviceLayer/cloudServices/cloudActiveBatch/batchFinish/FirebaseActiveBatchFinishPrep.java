/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchFinish;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.stepFinish.FirebaseActiveBatchGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.stepFinish.FirebaseActiveBatchStepFinish;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 6/22/2018
 * Project : Driver
 */
public class FirebaseActiveBatchFinishPrep implements
        CloudActiveBatchInterface.ActiveBatchUpdated,
        CloudActiveBatchInterface.GetBatchDetailResponse {

    private static final String TAG = "FirebaseActiveBatchFinishPrep";

    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private ActiveBatchManageInterface.ActorType actorType;
    private String batchGuid;
    private CloudActiveBatchInterface.FinishActiveBatchResponse response;

    private BatchDetail batchDetail;

    public void finishBatchRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef,
                                   ActiveBatchManageInterface.ActorType actorType, String batchGuid, CloudActiveBatchInterface.FinishActiveBatchResponse response){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef   = " + batchDataRef.toString());
        Timber.tag(TAG).d("actorType      = " + actorType.toString());
        Timber.tag(TAG).d("batchGuid      = " + batchGuid);

        this.activeBatchRef = activeBatchRef;
        this.batchDataRef = batchDataRef;
        this.actorType = actorType;
        this.batchGuid = batchGuid;
        this.response = response;

        Timber.tag(TAG).d("getting active batch data...");
        new FirebaseActiveBatchGet().getActiveBatchRequest(activeBatchRef, batchDataRef, this);
    }

    ///
    /// Positive response to FirebaseActiveBatchGet.  response we got matches what we expected, so we continue
    ///

    public void batchWaitingToFinish(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).d("   ...got batchWaitingToFinish -> this is what we want");

        ///compare batchGuid we got with the one user requested -> they should match
        if (batchGuid.equals(this.batchGuid)){
            //now get the batchDetail for this batchGuid
            Timber.tag(TAG).d("batchGuids MATCH, now getting batchDetail");
            new FirebaseActiveBatchDetailGet().getBatchDetailRequest(batchDataRef, batchGuid, this);
        } else {
            Timber.tag(TAG).w("batchGuids DON'T match, this should never happen.  user passed in -> " + this.batchGuid + ", active batch -> " + batchGuid);
            response.cloudActiveBatchFinished();
        }
    }

    ////
    ////    Response to FirebaseActiveBatchDetailGet
    ////
    public void cloudGetActiveBatchDetailSuccess(BatchDetail batchDetail){
        Timber.tag(TAG).d("      cloudGetActiveBatchDetailSuccess");
        new FirebaseActiveBatchFinish().finishBatchRequest(activeBatchRef, batchDataRef, actorType, batchDetail, response);
    }

    public void cloudGetActiveBatchDetailFailure(){
        Timber.tag(TAG).w("      cloudGetActiveBatchDetailSuccess -> should never get this");
        response.cloudActiveBatchFinished();
    }

    ////
    ////    Negative responses to FirebaseActiveBatchGet.  Response we got doesn't match what was expected, so we bail out
    ////

    public void stepStarted(ActiveBatchManageInterface.ActorType actorType, ActiveBatchManageInterface.ActionType actionType,
                            Boolean batchStarted, Boolean orderStarted, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){

        Timber.tag(TAG).w("   ......got stepStarted -> this should never happen");
        response.cloudActiveBatchFinished();
    }

    public void batchFinished(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).w("   ...got batchFinished -> this should never happen");
        response.cloudActiveBatchFinished();
    }

    public void batchRemoved(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).w("   ...got batchRemoved -> this should never happen");
        response.cloudActiveBatchFinished();
    }

    public void noBatch(){
        Timber.tag(TAG).w("   ...got noBatch -> this should never happen");
        response.cloudActiveBatchFinished();
    }
}
