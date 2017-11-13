/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.batchDetail.FirebaseBatchDetailSetStatus;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.serviceOrders.FirebaseServiceOrderSetStatus;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepGet;
import it.flube.driver.deviceLayer.cloudDatabase.batchData.steps.FirebaseOrderStepSetWorkStage;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class ActiveBatchNodeWithBatchData implements
        CloudDatabaseInterface.GetBatchDetailResponse,
        FirebaseServiceOrderGet.GetServiceOrderResponse,
        FirebaseOrderStepGet.GetOrderStepResponse,
        FirebaseBatchDetailSetStatus.Response,
        FirebaseServiceOrderSetStatus.Response,
        FirebaseOrderStepSetWorkStage.Response {

    private static final String TAG = "ActiveBatchNodeWithoutBatchData";

    private DatabaseReference batchDataRef;
    private ActiveBatchNode nodeData;
    private CloudDatabaseInterface.ActiveBatchUpdated response;

    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;
    private OrderStepInterface step;


    public void processNode(DatabaseReference batchDataRef, ActiveBatchNode nodeData, CloudDatabaseInterface.ActiveBatchUpdated response){
        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());

        this.batchDataRef = batchDataRef;
        this.nodeData = nodeData;
        this.response = response;

        Timber.tag(TAG).d("getting batch detail for guid : " + nodeData.getBatchGuid());
        new FirebaseBatchDetailGet().getBatchDetailRequest(batchDataRef, nodeData.getBatchGuid(),this);
    }

    /// response to request for batchDetail
    public void cloudDatabaseGetBatchDetailSuccess(BatchDetail batchDetail) {
        Timber.tag(TAG).d("   ...got batchDetail!");
        this.batchDetail = batchDetail;

        // now get service order
        Timber.tag(TAG).d("getting service order for order sequence : " + nodeData.getServiceOrderSequence());
        new FirebaseServiceOrderGet().getServiceOrderRequest(batchDataRef, batchDetail.getBatchGuid(), nodeData.getServiceOrderSequence(), this);
    }

    public void cloudDatabaseGetBatchDetailFailure() {
        Timber.tag(TAG).w("   ...could not get batchDetail : response -> noBatch");
        response.noBatch();
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
        Timber.tag(TAG).w("      ...could not get service order : response -> noBatch");
        response.noBatch();
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
        response.noBatch();
    }

    private void determineResponse(){
        Timber.tag(TAG).d("      Determinining Response...");
        Timber.tag(TAG).d("         ...actionType -> " + nodeData.getActionType());
        Timber.tag(TAG).d("         ...actorType  -> " + nodeData.getActorType());

        switch (nodeData.getActionType()){
            case BATCH_STARTED:
                new FirebaseBatchDetailSetStatus().setBatchDetailStatusRequest(batchDataRef, batchDetail, BatchDetail.WorkStatus.ACTIVE, this);
                break;

            case ORDER_STARTED:
                new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder, ServiceOrder.ServiceOrderStatus.ACTIVE, this);
                break;

            case STEP_STARTED:
                new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.ACTIVE, this);
                response.stepStarted(nodeData.getActorType(), nodeData.getActionType(), batchDetail, serviceOrder, step);
                break;

            case NO_BATCH:
            case NOT_SPECIFIED:
            case BATCH_REMOVED:
            case BATCH_FINISHED:
                // should never see these action types WITH batch data.
                Timber.tag(TAG).w("         ...this action type should NEVER have batch data --> " + nodeData.getActionType().toString());
                Timber.tag(TAG).d("         ...response -> noBatch");
                response.noBatch();
                break;
        }
    }

    public void setBatchDetailStatusComplete(){
        Timber.tag(TAG).d("         ...set batch detail workstatus ACTIVE");
        new FirebaseServiceOrderSetStatus().setServiceOrderStatusRequest(batchDataRef, serviceOrder, ServiceOrder.ServiceOrderStatus.ACTIVE, this);
    }

    public void setServiceOrderStatusComplete(){
        Timber.tag(TAG).d("         ...set service order workstage ACTIVE");
        new FirebaseOrderStepSetWorkStage().setOrderStepSetWorkStageRequest(batchDataRef, step, OrderStepInterface.WorkStage.ACTIVE, this);
    }

    public void setOrderStepWorkStageComplete(){
        Timber.tag(TAG).d("         ...set step workstage ACTIVE");
        response.stepStarted(nodeData.getActorType(), nodeData.getActionType(), batchDetail, serviceOrder, step);
    }

}
