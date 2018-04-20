/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.stepFinish;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class FirebaseActiveBatchStepFinishPrep implements
    CloudActiveBatchInterface.ActiveBatchUpdated {

    private static final String TAG = "FirebaseActiveBatchStepFinishPrep";

    private CloudActiveBatchInterface.FinishActiveBatchStepResponse response;
    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private ActiveBatchManageInterface.ActorType actorType;



    public void finishStepRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef,
                                  ActiveBatchManageInterface.ActorType actorType, CloudActiveBatchInterface.FinishActiveBatchStepResponse response){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        this.activeBatchRef = activeBatchRef;
        this.batchDataRef = batchDataRef;
        this.actorType = actorType;
        this.response = response;

        Timber.tag(TAG).d("getting active batch data...");
        new FirebaseActiveBatchGet().getActiveBatchRequest(activeBatchRef, batchDataRef, this);
    }

    public void stepStarted(ActiveBatchManageInterface.ActorType actorType, ActiveBatchManageInterface.ActionType actionType,
                            BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){

        Timber.tag(TAG).d("   ...got active batch data, now finishing the step");
        new FirebaseActiveBatchStepFinish().finishStepRequest(activeBatchRef, batchDataRef, this.actorType,
                batchDetail, serviceOrder, step, this.response);

    }

    public void batchFinished(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).w("   ...got batchFinished -> this should never happen");
        response.cloudActiveBatchFinishStepComplete();
    }

    public void batchRemoved(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).w("   ...got batchFinished -> this should never happen");
        response.cloudActiveBatchFinishStepComplete();
    }

    public void noBatch(){
        Timber.tag(TAG).w("   ...got batchFinished -> this should never happen");
        response.cloudActiveBatchFinishStepComplete();
    }

}
