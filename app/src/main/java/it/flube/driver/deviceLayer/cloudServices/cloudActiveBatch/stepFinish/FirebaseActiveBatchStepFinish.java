/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.stepFinish;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchStart.FirebaseActiveBatchSetData;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.batchDetail.FirebaseBatchDetailSetStatus;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.serviceOrders.FirebaseServiceOrderSetStatus;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.steps.FirebaseOrderStepSetWorkStage;
import it.flube.driver.modelLayer.entities.ResponseCounter;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

import static it.flube.libbatchdata.interfaces.ActiveBatchManageInterface.ActionType.ORDER_STARTED;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class FirebaseActiveBatchStepFinish implements
        FirebaseOrderStepSetWorkStage.Response,
        FirebaseServiceOrderSetStatus.Response,
        FirebaseActiveBatchSetData.Response {

    private static final String TAG = "FirebaseActiveBatchStepFinish";

    private DatabaseReference activeBatchRef;
    private DatabaseReference batchDataRef;
    private ActiveBatchManageInterface.ActorType actorType;
    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private OrderStepInterface step;

    private Boolean markOrderComplete;
    private ActiveBatchManageInterface.ActionType nextActionType;

    private ResponseCounter responseCounter;

    private CloudActiveBatchInterface.FinishActiveBatchStepResponse response;

    public void finishStepRequest(DatabaseReference activeBatchRef, DatabaseReference batchDataRef,
                                  ActiveBatchManageInterface.ActorType actorType,
                                  BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface step,
                                  CloudActiveBatchInterface.FinishActiveBatchStepResponse response) {

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        //determine the next step.  Possibilities are:
        // 1. There is another step after the CURRENT STEP in the ACTIVE ORDER
        // 2. This is the LAST STEP in the ACTIVE ORDER, and this is the LAST ORDER in the ACTIVE BATCH
        // 3. This is the LAST STEP in the ACTIVE ORDER, and there are additional orders in the ACITVE BATCH

        this.activeBatchRef = activeBatchRef;
        this.batchDataRef = batchDataRef;
        this.actorType = actorType;
        this.batchDetail = batchDetail;
        this.serviceOrder = serviceOrder;
        this.step = step;

        this.response = response;



        Timber.tag(TAG).d("finishStepRequest START...");

        //// first determine what we are going to do.  There are 3 options:
        ///
        /// 1. there are more STEPS in the current order (mark STEP complete, start new Step with STEP_STARTED action type)
        /// 2. there are no more STEPS in this order, but there are more orders in this batch (mark Step Complete, mark Order Complete, start new order with ORDER_STARTED action type)
        /// 3. this was the last step of the last order in the batch (mark step complete, mark order complete, mark batch complete, set batch WAITING_TO_FINISH action type)

        if (isThereAnotherStepInThisOrder()) {
            markOrderComplete = false;
            nextActionType = ActiveBatchManageInterface.ActionType.STEP_STARTED;
        } else if (isThereAnotherOrderInThisBatch()) {
            markOrderComplete = true;
            nextActionType = ActiveBatchManageInterface.ActionType.ORDER_STARTED;
        } else {
            markOrderComplete = true;
            nextActionType = ActiveBatchManageInterface.ActionType.BATCH_WAITING_TO_FINISH;
        }
        Timber.tag(TAG).d("   markOrderComplete -> " + markOrderComplete.toString());
        Timber.tag(TAG).d("   nextActionType    -> " + nextActionType.toString());

        //first set that this step is COMPLETE
        new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.COMPLETED, this);

    }

    private Boolean isThereAnotherStepInThisOrder(){
        return (step.getSequence() < serviceOrder.getTotalSteps());
    }

    private Boolean isThereAnotherOrderInThisBatch(){
        return (!isThereAnotherStepInThisOrder() && (serviceOrder.getSequence() < batchDetail.getServiceOrderCount()));
    }


    public void setOrderStepWorkStageComplete(){
        Timber.tag(TAG).d("   ...setOrderStepWorkStageComplete");

        if (markOrderComplete){
            new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder,ServiceOrder.ServiceOrderStatus.COMPLETED, this);
        } else {
            setNextActionType();
        }
    }

    public void setServiceOrderStatusComplete(){
        Timber.tag(TAG).d("   ...setServiceOrderStatusComplete");
            setNextActionType();
    }


    private void setNextActionType(){
        Timber.tag(TAG).d("setting next action type -> " + nextActionType.toString());
        switch (nextActionType){
            case STEP_STARTED:
                new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), serviceOrder.getSequence(), step.getSequence() + 1,
                        ActiveBatchManageInterface.ActionType.STEP_STARTED, actorType,this);
                break;
            case ORDER_STARTED:
                new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), serviceOrder.getSequence()+1, 1,
                        ORDER_STARTED, actorType, this);
                break;
            case BATCH_WAITING_TO_FINISH:
                new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), null, null,
                        ActiveBatchManageInterface.ActionType.BATCH_WAITING_TO_FINISH, actorType, this);
                break;
        }
    }

    public void setDataComplete() {
        Timber.tag(TAG).d("   ...setDataComplete");
        Timber.tag(TAG).d("...finishStepRequest COMPLETE");
        response.cloudActiveBatchFinishStepComplete();
    }



}
