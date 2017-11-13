/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 11/8/2017
 * Project : Driver
 */

public class FirebaseActiveBatchStepFinishPrep implements
    CloudDatabaseInterface.ActiveBatchUpdated {
    private static final String TAG = "FirebaseActiveBatchStepFinishPrep";

    private CloudDatabaseInterface.FinishActiveBatchStepResponse response;
    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private CloudDatabaseInterface.ActorType actorType;



    public void finishStepRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef,
                             CloudDatabaseInterface.ActorType actorType, CloudDatabaseInterface.FinishActiveBatchStepResponse response){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        this.activeBatchRef = activeBatchRef;
        this.batchDataRef = batchDataRef;
        this.actorType = actorType;
        this.response = response;

        Timber.tag(TAG).d("getting active batch data...");
        new FirebaseActiveBatchGet().getActiveBatchRequest(activeBatchRef, batchDataRef, this);
    }

    public void stepStarted(CloudDatabaseInterface.ActorType actorType, CloudDatabaseInterface.ActionType actionType,
                            BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step){

        Timber.tag(TAG).d("   ...got active batch data, now finishing the step");
        new FirebaseActiveBatchStepFinish().finishStepRequest(activeBatchRef, batchDataRef, this.actorType,
                batchDetail, serviceOrder, step, this.response);

    }

    public void batchFinished(CloudDatabaseInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).w("   ...got batchFinished -> this should never happen");
        response.cloudDatabaseFinishActiveBatchStepComplete();
    }

    public void batchRemoved(CloudDatabaseInterface.ActorType actorType, String batchGuid){
        Timber.tag(TAG).w("   ...got batchFinished -> this should never happen");
        response.cloudDatabaseFinishActiveBatchStepComplete();
    }

    public void noBatch(){
        Timber.tag(TAG).w("   ...got batchFinished -> this should never happen");
        response.cloudDatabaseFinishActiveBatchStepComplete();
    }

}
