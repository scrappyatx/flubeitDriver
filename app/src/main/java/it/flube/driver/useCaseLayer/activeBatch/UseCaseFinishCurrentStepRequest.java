/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 10/28/2017
 * Project : Driver
 */

public class UseCaseFinishCurrentStepRequest implements
        Runnable,
        CloudDatabaseInterface.ServiceOrderStatusUpdated,
        CloudDatabaseInterface.OrderStepWorkStageUpdated,
        CloudDatabaseInterface.BatchDetailStatusUpdated,
        CloudDatabaseInterface.ActiveBatchNodesUpdated {

    private enum CaseType {
        CaseOne,
        CaseTwo,
        CaseThree
    }

    private MobileDeviceInterface device;
    private Response response;
    private CaseType caseType;


    public UseCaseFinishCurrentStepRequest(MobileDeviceInterface device, Response response){
        this.device = device;
        this.response = response;
    }

    public void run(){
        //get the current step
        BatchDetail batchDetail=device.getActiveBatch().getBatchDetail();
        ServiceOrder serviceOrder=device.getActiveBatch().getServiceOrder();
        OrderStepInterface step = device.getActiveBatch().getStep();

        //determine the next step.  Possibilities are:
        // 1. There is another step after the CURRENT STEP in the ACTIVE ORDER
        // 2. This is the LAST STEP in the ACTIVE ORDER, and this is the LAST ORDER in the ACTIVE BATCH
        // 3. This is the LAST STEP in the ACTIVE ORDER, and there are additional orders in the ACITVE BATCH

        if (step.getSequence() < serviceOrder.getTotalSteps()) {
            // There is ANOTHER STEP int this order
            caseType = CaseType.CaseOne;

            Integer nextSequence = step.getSequence() + 1;
            device.getCloudDatabase().setOrderStepWorkStageRequest(step, OrderStepInterface.WorkStage.COMPLETED, this);

            device.getCloudDatabase().setActiveBatchNodesRequest(step.getBatchGuid(), serviceOrder.getSequence(), nextSequence, this);

        } else if (serviceOrder.getSequence() < batchDetail.getServiceOrderCount()) {
            // This is the last step in the active order.  BUT There is ANOTHER ORDER
            Integer nextSequence = serviceOrder.getSequence()+1;
            caseType = CaseType.CaseTwo;

            device.getCloudDatabase().setOrderStepWorkStageRequest(step, OrderStepInterface.WorkStage.COMPLETED, this);
            device.getCloudDatabase().setServiceOrderStatusRequest(serviceOrder, ServiceOrder.ServiceOrderStatus.COMPLETED, this);

            device.getCloudDatabase().setActiveBatchNodesRequest(step.getBatchGuid(), nextSequence, 1, this);


        } else {
            // This is the last step in the active order. ORDER is COMPLETE
            caseType = CaseType.CaseThree;

            device.getCloudDatabase().setOrderStepWorkStageRequest(step, OrderStepInterface.WorkStage.COMPLETED, this);
            device.getCloudDatabase().setServiceOrderStatusRequest(serviceOrder, ServiceOrder.ServiceOrderStatus.COMPLETED, this);
            device.getCloudDatabase().setBatchDetailStatusRequest(batchDetail, BatchDetail.WorkStatus.COMPLETED_SUCCESS, this);
            device.getCloudDatabase().setActiveBatchFinishedServerNode(batchDetail.getBatchGuid());

            device.getCloudDatabase().setActiveBatchNodesNullRequest(this);
        }
    }


    public void cloudDatabaseBatchDetailStatusSetComplete(){

    }

    public void cloudDatabaseServiceOrderStatusSetComplete(){

    }

    public void cloudDatabaseOrderStepWorkStageSetComplete(){

    }

    public void cloudDatabaseActiveBatchNodeSetComplete(){
        switch (caseType){
            case CaseOne:
                response.finishCurrentStepWithStepComplete();
                break;
            case CaseTwo:
                response.finishCurrentStepWithOrderComplete();
                break;
            case CaseThree:
                response.finishCurrentStepWithBatchComplete();
                break;
        }
    }


    public interface Response {
        void finishCurrentStepWithStepComplete();

        void finishCurrentStepWithBatchComplete();

        void finishCurrentStepWithOrderComplete();
    }
}
