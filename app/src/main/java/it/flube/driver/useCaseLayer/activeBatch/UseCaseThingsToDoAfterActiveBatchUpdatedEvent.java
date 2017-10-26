/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 10/16/2017
 * Project : Driver
 */

public class UseCaseThingsToDoAfterActiveBatchUpdatedEvent implements
        Runnable,
        CloudDatabaseInterface.SaveCurrentActiveBatchDataResponse {


    private final MobileDeviceInterface device;
    private final BatchDetail batchDetail;
    private final ServiceOrder serviceOrder;
    private final OrderStepInterface orderStep;
    private final Boolean hasNewBatch;
    private final UseCaseThingsToDoAfterActiveBatchUpdatedEvent.Response response;



    public UseCaseThingsToDoAfterActiveBatchUpdatedEvent(MobileDeviceInterface device, Response response){
        this.device = device;
        this.response = response;
        this.batchDetail = null;
        this.serviceOrder = null;
        this.orderStep = null;
        hasNewBatch = false;

    }

    public UseCaseThingsToDoAfterActiveBatchUpdatedEvent(MobileDeviceInterface device, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep, Response response){
        this.device = device;
        this.batchDetail = batchDetail;
        this.serviceOrder = serviceOrder;
        this.orderStep = orderStep;
        this.response = response;
        hasNewBatch = true;

    }

    public void run(){
        /// just got a change in the active batch from the clouddb.  Need to save what is in the device "active batch" global
        /// possibilities are:
        ///
        ///  "current" active batch EMPTY, "new" active batch has data ==> BATCH STARTED
        ///  "current" active batch EMPTY, "new" active batch EMPTY ==> nothing
        ///
        ///  "current" active batch has data, "new" active batch EMPTY ==> BATCH COMPLETED
        ///
        ///  "current" and "new" active batch have data, "current" batchDetail.guid != "new" batchDetail.guid ==> BATCH STARTED
        ///  "current" and "new" active batch have data, "current" serviceOrder != "new" serviceOrder ==> SERVICE ORDER STARTED
        ///  "current" and "new" active batch have data, "current" step != "new" step


        //Step 1 - Save Current State
        saveCurrentState();

        if (bothEmpty()) {
            response.useCaseThingsToDoAfterActiveBatchUpdatedEventComplete();
        }


    }

    private void putNewIntoCurrent(){
        if (hasCurrentBatch()) {
            device.getActiveBatch().setActiveBatch(batchDetail, serviceOrder, orderStep);
        } else {
            device.getActiveBatch().setActiveBatch();
        }
    }

    private void saveCurrentState(){
        if (hasCurrentBatch()) {
            device.getCloudDatabase().saveCurrentActiveBatchData(batchDetail, serviceOrder, orderStep, this);
        } else {
            cloudDatabaseSaveCurrentActiveBatchDataComplete();
        }
    }

    public void cloudDatabaseSaveCurrentActiveBatchDataComplete() {
        //Step 2 - Put new state info into active batch
        putNewIntoCurrent();

        //Step 3 - figure out what happened and send appropriate response
    }

    private Boolean bothEmpty(){
        if (!hasCurrentBatch() && !hasNewBatch) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean hasCurrentBatch() {
        return device.getActiveBatch().hasActiveBatch();
    }

    private Boolean sameBatch() {
        if (hasCurrentBatch() && hasNewBatch) {

        } else {
        }

        return true;
    }

    private Boolean sameServiceOrder() {
        return true;
    }

    private Boolean nextStepInSequence() {
        return true;
    }

    private Boolean newBatchStarted() {
        if (hasCurrentBatch() && hasNewBatch) {
            //current TRUE, new TRUE
            saveCurrentState();

            if (device.getActiveBatch().getBatchDetail().getBatchGuid().equals(batchDetail.getBatchGuid())) {
                // there was a change in batch guid -> this is a new batch



            } else {

                if (device.getActiveBatch().getServiceOrder().getGuid().equals(serviceOrder.getGuid())) {

                } else {

                }

            }
            return true;

        } else if (!hasCurrentBatch() && hasNewBatch){
            //current FALSE, new TRUE
            return true;

        } else if (hasCurrentBatch() && !hasNewBatch) {
            //current TRUE, new FALSE
            return true;


        } else {
            //only thing left is both FALSE
            return false;

        }
    }


    interface Response {
        void useCaseThingsToDoAfterActiveBatchUpdatedEventComplete();
    }
}
