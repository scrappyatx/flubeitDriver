/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor.ActiveBatchNode;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.serviceOrders.FirebaseServiceOrderGet;
import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataManage.steps.FirebaseOrderStepGet;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/27/2018
 * Project : Driver
 */
public class FirebaseActiveBatchCurrentStepGet implements
        ValueEventListener,
        CloudActiveBatchInterface.GetBatchDetailResponse,
        FirebaseServiceOrderGet.GetServiceOrderResponse,
        FirebaseOrderStepGet.GetOrderStepResponse {

    private static final String TAG = "FirebaseActiveBatchCurrentStepGet";
    private static final String BATCH_DETAIL = "batchDetail";

    private DatabaseReference batchDataRef;
    private DatabaseReference activeBatchRef;
    private CloudActiveBatchInterface.GetActiveBatchCurrentStepResponse response;
    private ActiveBatchNode nodeData;

    private BatchDetail batchDetail;
    private ServiceOrder serviceOrder;

    public FirebaseActiveBatchCurrentStepGet(){}

    public void getActiveBatchCurrentStepRequest(DatabaseReference batchDataRef, DatabaseReference activeBatchRef, CloudActiveBatchInterface.GetActiveBatchCurrentStepResponse response){
        this.response = response;

        this.batchDataRef = batchDataRef;
        this.activeBatchRef = activeBatchRef;

        Timber.tag(TAG).d("batchDataRef   = " + batchDataRef.toString());
        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        activeBatchRef.addListenerForSingleValueEvent(this);
    }

    public void onDataChange(DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("onDataChange...");

        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...dataSnapshot exists!");
            try {
                nodeData = dataSnapshot.getValue(ActiveBatchNode.class);
                if (nodeData.hasAllBatchData()) {
                    /// we have a batchGuid, a serviceOrderSequence, and a stepSequence.  now get gets for service order and step
                    Timber.tag(TAG).d("      ...getting batch detail for guid : " + nodeData.getBatchGuid());
                    new FirebaseActiveBatchDetailGet().getBatchDetailRequest(batchDataRef, nodeData.getBatchGuid(),this);
                } else {
                    Timber.tag(TAG).d("      ...active batch node DOES NOT have all batch data");
                    response.cloudGetActiveBatchCurrentStepFailure();
                }
            } catch (Exception e) {
                Timber.tag(TAG).w("      ...error while trying to get active batch node data");
                Timber.tag(TAG).e(e);
                response.cloudGetActiveBatchCurrentStepFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).w("   ...dataSnapshot does not exist");
            response.cloudGetActiveBatchCurrentStepFailure();
        }

    }

    /// response to request for batchDetail
    public void cloudGetActiveBatchDetailSuccess(BatchDetail batchDetail) {
        Timber.tag(TAG).d("   ...got batchDetail -> " + batchDetail.getBatchGuid());
        this.batchDetail = batchDetail;

        // now get service order
        Timber.tag(TAG).d("   ...getting service order for order sequence : " + nodeData.getServiceOrderSequence());
        new FirebaseServiceOrderGet().getServiceOrderRequest(batchDataRef, batchDetail.getBatchGuid(), nodeData.getServiceOrderSequence(), this);
    }

    public void cloudGetActiveBatchDetailFailure() {
        Timber.tag(TAG).w("   ...could not get batchDetail : response -> noBatch");
        response.cloudGetActiveBatchCurrentStepFailure();
    }

    /// response to request for service order
    public void getServiceOrderSuccess(ServiceOrder serviceOrder){
        Timber.tag(TAG).d("      ...got serviceOrder -> " + serviceOrder.getGuid());
        this.serviceOrder = serviceOrder;

        //now get step
        Timber.tag(TAG).d("      ...getting step for step sequence : " + nodeData.getStepSequence());
        new FirebaseOrderStepGet().getOrderStep(batchDataRef, batchDetail.getBatchGuid(), serviceOrder.getGuid(), nodeData.getStepSequence(), this);
    }

    public void getServiceOrderFailure(){
        Timber.tag(TAG).w("      ...could not get service order : response -> noBatch");
        response.cloudGetActiveBatchCurrentStepFailure();
    }

    /// response to request for step
    public void getOrderStepSuccess(OrderStepInterface orderStep){
        Timber.tag(TAG).d("      ...got order step -> " + orderStep.getGuid());
        //now send response
        response.cloudGetActiveBatchCurrentStepSuccess(this.batchDetail, this.serviceOrder, orderStep);
    }

    public void getOrderStepFailure(){
        Timber.tag(TAG).w("      ...could not get orderStep : response -> noBatch");
        response.cloudGetActiveBatchCurrentStepFailure();
    }


    public void onCancelled(DatabaseError databaseError) {
        Timber.tag(TAG).e("onCancelled : error = " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetActiveBatchCurrentStepFailure();
    }

}


