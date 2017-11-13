/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.manageSteps;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudDatabase.activeBatch.FirebaseActiveBatchSetData;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailSetStatus;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderSetStatus;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepSetWorkStage;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseFinishCurrentStepRequest;
import timber.log.Timber;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class FirebaseActiveBatchStepFinish implements
        FirebaseOrderStepSetWorkStage.Response,
        FirebaseServiceOrderSetStatus.Response,
        FirebaseBatchDetailSetStatus.Response,
        FirebaseActiveBatchSetData.Response {

    private static final String TAG = "FirebaseActiveBatchStepFinish";

    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private OrderStepInterface step;

    private ResponseCounter responseCounter;

    private CloudDatabaseInterface.FinishActiveBatchStepResponse response;

    public void finishStepRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef,
                                  CloudDatabaseInterface.ActorType actorType,
                                  BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step,
                                  CloudDatabaseInterface.FinishActiveBatchStepResponse response) {

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        //determine the next step.  Possibilities are:
        // 1. There is another step after the CURRENT STEP in the ACTIVE ORDER
        // 2. This is the LAST STEP in the ACTIVE ORDER, and this is the LAST ORDER in the ACTIVE BATCH
        // 3. This is the LAST STEP in the ACTIVE ORDER, and there are additional orders in the ACITVE BATCH

        this.batchDetail = batchDetail;
        this.serviceOrder = serviceOrder;
        this.step = step;

        this.response = response;

        Timber.tag(TAG).d("finishStepRequest START...");

        if (isThereAnotherStepInThisOrder()){
            //have another step to do in this order
            Timber.tag(TAG).d("   ...have another step to do in this order");
            responseCounter = new ResponseCounter(2);

            Integer nextStepSequence = step.getSequence() + 1;
            new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.COMPLETED, this);

            new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), serviceOrder.getSequence(), nextStepSequence,
                    CloudDatabaseInterface.ActionType.STEP_STARTED, actorType,this);


        } else if (isThereAnotherOrderInThisBatch()){
            //have another order to do in this batch
            Timber.tag(TAG).d("   ...have another order to do in this batch");
            responseCounter = new ResponseCounter(3);

            Integer nextOrderSequence = serviceOrder.getSequence()+1;

            new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.COMPLETED, this);
            new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder,ServiceOrder.ServiceOrderStatus.COMPLETED, this);

            new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), nextOrderSequence, 1,
                    CloudDatabaseInterface.ActionType.ORDER_STARTED, actorType, this);


        } else {
            //finished with the batch
            Timber.tag(TAG).d("   ...batch is finished");
            responseCounter = new ResponseCounter(4);

            new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.COMPLETED, this);
            new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder,ServiceOrder.ServiceOrderStatus.COMPLETED, this);
            new FirebaseBatchDetailSetStatus().setBatchDetailStatusRequest(batchDataRef, batchDetail, BatchDetail.WorkStatus.COMPLETED_SUCCESS, this);

            new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), null, null,
                    CloudDatabaseInterface.ActionType.BATCH_FINISHED, actorType, this);
        }
        Timber.tag(TAG).d("...finishStepRequest COMPLETE");
    }


    private Boolean isThereAnotherStepInThisOrder(){
       return (step.getSequence() < serviceOrder.getTotalSteps());
    }

    private Boolean isThereAnotherOrderInThisBatch(){
        return (!isThereAnotherStepInThisOrder() && (serviceOrder.getSequence() < batchDetail.getServiceOrderCount()));
    }

    private void checkIfFinished(){
        responseCounter.onResponse();

        if (responseCounter.isFinished()){
            Timber.tag(TAG).d("      ...and we're done!");
            response.cloudDatabaseFinishActiveBatchStepComplete();
        } else {
            Timber.tag(TAG).d("      ...response " + responseCounter.getCount());
        }
    }

    public void setOrderStepWorkStageComplete() {
       checkIfFinished();
    }

    public void setServiceOrderStatusComplete() {
        checkIfFinished();
    }

    public void setBatchDetailStatusComplete() {
        checkIfFinished();
    }

    public void setDataComplete() {
        checkIfFinished();
    }

}
