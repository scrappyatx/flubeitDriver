/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 7/31/2018
 * Project : Driver
 */
public class UseCaseGetDriverAndActiveBatchCurrentStep implements
        Runnable,
        CloudActiveBatchInterface.GetActiveBatchCurrentStepResponse {

    private static final String TAG="UseCaseGetDriverAndActiveBatchCurrentStep";

    private final MobileDeviceInterface device;
    private final OrderStepInterface.TaskType expectedTaskType;
    private final Response response;
    private Driver driver;

    public UseCaseGetDriverAndActiveBatchCurrentStep(MobileDeviceInterface device, OrderStepInterface.TaskType expectedTaskType, Response response){
        this.device = device;
        this.expectedTaskType = expectedTaskType;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (device.getCloudAuth().hasDriver()) {
            //// get active batch step
            this.driver = device.getCloudAuth().getDriver();
            Timber.tag(TAG).d("...device has signed in user, get current active batch step");
            device.getCloudActiveBatch().getActiveBatchCurrentStepRequest(driver, this);

        } else {
            // no user
            Timber.tag(TAG).d("...there is no signed in user");
            response.useCaseGetDriverAndActiveBatchCurrentStepFailureNoDriverNoStep();
        }
    }

    //// GetActiveBatchCurrentStepRequest response interface
    public void cloudGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("cloudGetActiveBatchCurrentStepSuccess");
        if (expectedTaskType == orderStep.getTaskType()) {
            //task type matches what we were expecting
            Timber.tag(TAG).d("...taskType matches expectedTaskType ->" + expectedTaskType.toString());
            response.useCaseGetDriverAndActiveBatchCurrentStepSuccess(driver, batchDetail, serviceOrder, orderStep);
        } else {
            //task type doesn't match what we were expecting
            Timber.tag(TAG).d("...taskType (" + orderStep.getTaskType().toString() + "} doesn't match expectedTaskType ->" + expectedTaskType.toString());
            response.useCaseGetDriverAndActiveBatchCurrentStepFailureStepMismatch(driver, orderStep.getTaskType());
        }
    }

    public void cloudGetActiveBatchCurrentStepFailure(){
        Timber.tag(TAG).d("cloudGetActiveBatchCurrentStepFailure");
        response.useCaseGetDriverAndActiveBatchCurrentStepFailureDriverOnly(driver);
    }

    public interface Response {
        void useCaseGetDriverAndActiveBatchCurrentStepSuccess(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep);

        void useCaseGetDriverAndActiveBatchCurrentStepFailureDriverOnly(Driver driver);

        void useCaseGetDriverAndActiveBatchCurrentStepFailureNoDriverNoStep();

        void useCaseGetDriverAndActiveBatchCurrentStepFailureStepMismatch(Driver driver, OrderStepInterface.TaskType foundTaskType);
    }
}
