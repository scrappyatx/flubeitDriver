/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet.FirebaseActiveBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.batchDetail.FirebaseBatchDetailGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.batchDetail.FirebaseBatchDetailSetStatus;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.serviceOrders.FirebaseServiceOrderGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.serviceOrders.FirebaseServiceOrderSetStatus;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.steps.FirebaseOrderStepGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.steps.FirebaseOrderStepSetWorkStage;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class ActiveBatchNodeWithBatchData implements
        CloudActiveBatchInterface.GetBatchDetailResponse,
        FirebaseServiceOrderGet.GetServiceOrderResponse,
        FirebaseOrderStepGet.GetOrderStepResponse,
        FirebaseBatchDetailSetStatus.Response,
        FirebaseServiceOrderSetStatus.Response,
        FirebaseOrderStepSetWorkStage.Response {

    private static final String TAG = "ActiveBatchNodeWithBatchData";

    private DatabaseReference batchDataRef;
    private ActiveBatchNode nodeData;
    private CloudActiveBatchInterface.ActiveBatchUpdated response;

    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private OrderStepInterface step;

    private Boolean batchStarted;
    private Boolean orderStarted;

    public void processNode(DatabaseReference batchDataRef, ActiveBatchNode nodeData, CloudActiveBatchInterface.ActiveBatchUpdated response){
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        this.batchDataRef = batchDataRef;
        this.nodeData = nodeData;
        this.response = response;

        Timber.tag(TAG).d("getting batch detail for guid : " + nodeData.getBatchGuid());
        new FirebaseActiveBatchDetailGet().getBatchDetailRequest(batchDataRef, nodeData.getBatchGuid(),this);
    }

    /// response to request for batchDetail
    public void cloudGetActiveBatchDetailSuccess(BatchDetail batchDetail) {
        Timber.tag(TAG).d("   ...got batchDetail!");
        this.batchDetail = batchDetail;

        // now get service order
        Timber.tag(TAG).d("getting service order for order sequence : " + nodeData.getServiceOrderSequence());
        new FirebaseServiceOrderGet().getServiceOrderRequest(batchDataRef, batchDetail.getBatchGuid(), nodeData.getServiceOrderSequence(), this);
    }

    public void cloudGetActiveBatchDetailFailure() {
        Timber.tag(TAG).w("   ...could not get batchDetail : response -> dataMismatchOnNode");
        response.dataMismatchOnNode();
    }

    /// response to request for service order
    public void getServiceOrderSuccess(ServiceOrder serviceOrder){
        Timber.tag(TAG).d("      ...got serviceOrder!");
        this.serviceOrder = serviceOrder;

        //now get step
        Timber.tag(TAG).d("getting step for step sequence : " + nodeData.getStepSequence());
        new FirebaseOrderStepGet().getOrderStep(batchDataRef, batchDetail.getBatchGuid(), serviceOrder.getGuid(), nodeData.getStepSequence(), this);
    }

    public void getServiceOrderFailure(){
        Timber.tag(TAG).w("      ...could not get service order : response -> dataMismatchOnNode");
        response.dataMismatchOnNode();
    }

    /// response to request for step
    public void getOrderStepSuccess(OrderStepInterface orderStep){
        Timber.tag(TAG).d("      ...got step!");
        this.step = orderStep;
        //now send response
        determineResponse();
    }

    public void getOrderStepFailure(){
        Timber.tag(TAG).w("      ...could not get orderStep : response -> noBatch");
        response.dataMismatchOnNode();
    }

    private void determineResponse(){
        Timber.tag(TAG).d("      Determinining Response...");
        Timber.tag(TAG).d("         ...actionType -> " + nodeData.getActionType());
        Timber.tag(TAG).d("         ...actorType  -> " + nodeData.getActorType());

        batchStarted = false;
        orderStarted = false;

        switch (nodeData.getActionType()){
            //TODO need to implement a way to set batch, order & step started timestamp ONE TIME ONLY, on the first time it is done.  This may be triggered many times, but only write timestamp on the first time

            case BATCH_STARTED:
                batchStarted = true;
                new FirebaseBatchDetailSetStatus().setBatchDetailStatusRequest(batchDataRef, batchDetail, BatchDetail.WorkStatus.ACTIVE, this);
                break;

            case ORDER_STARTED:
                orderStarted = true;
                new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder, ServiceOrder.ServiceOrderStatus.ACTIVE, this);
                break;

            case STEP_STARTED:
                new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.ACTIVE, this);
                break;

            case NO_BATCH:
            case NOT_SPECIFIED:
            case BATCH_REMOVED:
            case BATCH_WAITING_TO_FINISH:
            case BATCH_FINISHED:
                // should never see these action types WITH batch data.
                Timber.tag(TAG).w("         ...this action type should NEVER have batch data --> " + nodeData.getActionType().toString());
                Timber.tag(TAG).d("         ...response -> noBatch");
                response.dataMismatchOnNode();
                break;
        }
    }

    //// response to BATCH STARTED
    public void setBatchDetailStatusComplete(){
        Timber.tag(TAG).d("         ...set batch detail workstatus ACTIVE");
        new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder, ServiceOrder.ServiceOrderStatus.ACTIVE, this);
    }

    //// response to ORDER STARTED
    public void setServiceOrderStatusComplete(){
        Timber.tag(TAG).d("         ...set service order workstage ACTIVE");
        new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.ACTIVE, this);
    }

    //// response to STEP STARTED
    public void setOrderStepWorkStageComplete(){
        Timber.tag(TAG).d("         ...set step workstage ACTIVE");
        response.stepStarted(nodeData.getActorType(), nodeData.getActionType(), batchStarted, orderStarted, batchDetail, serviceOrder, step);
    }

}
