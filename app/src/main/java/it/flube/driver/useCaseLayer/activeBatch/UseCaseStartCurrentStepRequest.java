/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class UseCaseStartCurrentStepRequest implements
        Runnable,
        CloudDatabaseInterface.ServiceOrderStatusUpdated,
        CloudDatabaseInterface.OrderStepWorkStageUpdated,
        CloudDatabaseInterface.BatchDetailStatusUpdated {

    private MobileDeviceInterface device;
    private Response response;

    public UseCaseStartCurrentStepRequest(MobileDeviceInterface device, Response response){
        this.device = device;
        this.response = response;
    }

    public void run(){
        //get the current step
        BatchDetail batchDetail=device.getActiveBatch().getBatchDetail();
        ServiceOrder serviceOrder=device.getActiveBatch().getServiceOrder();
        OrderStepInterface step = device.getActiveBatch().getStep();

        if (batchDetail.getWorkStatus() != BatchDetail.WorkStatus.ACTIVE) {
            device.getCloudDatabase().setBatchDetailStatusRequest(batchDetail, BatchDetail.WorkStatus.ACTIVE, this);
            //device.getCloudDatabase().setActiveBatchStartedServerNode(batchDetail.getBatchGuid());
        }

        if (serviceOrder.getStatus() != ServiceOrder.ServiceOrderStatus.ACTIVE) {
            device.getCloudDatabase().setServiceOrderStatusRequest(serviceOrder, ServiceOrder.ServiceOrderStatus.ACTIVE, this);
        }

        if (step.getWorkStage() != OrderStepInterface.WorkStage.ACTIVE) {
            device.getCloudDatabase().setOrderStepWorkStageRequest(step, OrderStepInterface.WorkStage.ACTIVE, this);
        }

        if (device.getLocationTelemetry().hasLastGoodPosition()){
            LatLonLocation driverPosition = device.getLocationTelemetry().getLastGoodPosition();
            device.getCloudDatabase().setActiveBatchStartedServerNode(batchDetail, serviceOrder, step, driverPosition);
        } else {
            device.getCloudDatabase().setActiveBatchStartedServerNode(batchDetail, serviceOrder, step);
        }

        response.startCurrentStepComplete();
    }

    public void cloudDatabaseBatchDetailStatusSetComplete(){

    }

    public void cloudDatabaseServiceOrderStatusSetComplete(){

    }

    public void cloudDatabaseOrderStepWorkStageSetComplete(){

    }

    public interface Response {
        void startCurrentStepComplete();
    }
}
