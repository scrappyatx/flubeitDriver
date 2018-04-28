/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/27/2018
 * Project : Driver
 */
public class UseCaseCompareActivtyLaunchDataToCurrentStep implements
    Runnable,
    UseCaseGetActiveBatchCurrentStep.Response {

    private final static String TAG = "UseCaseCompareActivtyLaunchDataToCurrentStep";

    private final MobileDeviceInterface device;
    private final Response response;
    private final String batchGuid;
    private final String serviceOrderGuid;
    private final String orderStepGuid;
    private final OrderStepInterface.TaskType taskType;

    public UseCaseCompareActivtyLaunchDataToCurrentStep(MobileDeviceInterface device,
                                                        String batchGuid, String serviceOrderGuid, String orderStepGuid, OrderStepInterface.TaskType taskType,
                                                        Response response){

        this.device = device;
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.orderStepGuid = orderStepGuid;
        this.taskType = taskType;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        new UseCaseGetActiveBatchCurrentStep(device, this).run();
    }

    //// response interface for UseCaseGetActiveBatchCurrentStep
    public void useCaseGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("useCaseGetActiveBatchCurrentStepSuccess");

        if (batchDetail.getBatchGuid().equals(batchGuid)){
            if (serviceOrder.getGuid().equals(serviceOrderGuid)){
                if (orderStep.getGuid().equals(orderStepGuid)){
                    if (orderStep.getTaskType().equals(taskType)){
                        Timber.tag(TAG).d("current step data matches activity launch data");
                        response.useCaseCurrentStepMatchesActivityLaunchData(batchDetail, serviceOrder, orderStep, orderStep.getTaskType());
                    } else {
                        Timber.tag(TAG).d("taskType does not match");
                        response.useCaseCurrentStepDoesNotMatchActivityLaunchData(batchDetail, serviceOrder, orderStep, orderStep.getTaskType());
                    }
                } else {
                    Timber.tag(TAG).d("orderStepGuid does not match");
                    response.useCaseCurrentStepDoesNotMatchActivityLaunchData(batchDetail, serviceOrder, orderStep, orderStep.getTaskType());
                }
            } else {
                Timber.tag(TAG).d("serviceOrderGuid does not match");
                response.useCaseCurrentStepDoesNotMatchActivityLaunchData(batchDetail, serviceOrder, orderStep, orderStep.getTaskType());
            }
        } else {
            Timber.tag(TAG).d("batchGuid does not match");
            response.useCaseCurrentStepDoesNotMatchActivityLaunchData(batchDetail, serviceOrder, orderStep, orderStep.getTaskType());
        }
    }

    public void useCaseGetActiveBatchCurrentStepFailure(){
        Timber.tag(TAG).d("useCaseGetActiveBatchCurrentStepFailure");
        response.useCaseNoCurrentStepData();
    }


    public interface Response {
        void useCaseCurrentStepMatchesActivityLaunchData(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep, OrderStepInterface.TaskType taskType);

        void useCaseCurrentStepDoesNotMatchActivityLaunchData(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep, OrderStepInterface.TaskType taskType);

        void useCaseNoCurrentStepData();
    }
}
