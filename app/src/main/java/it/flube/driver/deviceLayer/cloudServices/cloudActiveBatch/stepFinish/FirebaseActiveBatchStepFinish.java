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

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class FirebaseActiveBatchStepFinish implements
        FirebaseOrderStepSetWorkStage.Response,
        FirebaseServiceOrderSetStatus.Response,
        FirebaseActiveBatchSetData.Response {

    private static final String TAG = "FirebaseActiveBatchStepFinish";

    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private OrderStepInterface step;

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
                    ActiveBatchManageInterface.ActionType.STEP_STARTED, actorType,this);


        } else if (isThereAnotherOrderInThisBatch()){
            //have another order to do in this batch
            Timber.tag(TAG).d("   ...have another order to do in this batch");
            responseCounter = new ResponseCounter(3);

            Integer nextOrderSequence = serviceOrder.getSequence()+1;

            new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.COMPLETED, this);
            new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder,ServiceOrder.ServiceOrderStatus.COMPLETED, this);

            new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), nextOrderSequence, 1,
                    ActiveBatchManageInterface.ActionType.ORDER_STARTED, actorType, this);


        } else {
            //finished with the batch
            Timber.tag(TAG).d("   ...batch is finished");
            responseCounter = new ResponseCounter(3);

            new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.COMPLETED, this);
            new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder,ServiceOrder.ServiceOrderStatus.COMPLETED, this);

            new FirebaseActiveBatchSetData().setDataRequest(activeBatchRef, batchDetail.getBatchGuid(), null, null,
                    ActiveBatchManageInterface.ActionType.BATCH_WAITING_TO_FINISH, actorType, this);
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
            response.cloudActiveBatchFinishStepComplete();
        } else {
            Timber.tag(TAG).d("      ...response " + responseCounter.getCount());
        }
    }

    public void setOrderStepWorkStageComplete() {
        Timber.tag(TAG).d("   ...setOrderStepWorkStageComplete");
       checkIfFinished();
    }

    public void setServiceOrderStatusComplete() {
        Timber.tag(TAG).d("   ...setServiceOrderStatusComplete");
        checkIfFinished();
    }

    public void setDataComplete() {
        Timber.tag(TAG).d("   ...setDataComplete");
        checkIfFinished();
    }

}
