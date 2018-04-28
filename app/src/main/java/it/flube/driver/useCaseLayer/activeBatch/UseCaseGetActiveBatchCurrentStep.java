/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/27/2018
 * Project : Driver
 */
public class UseCaseGetActiveBatchCurrentStep implements
        Runnable,
        CloudActiveBatchInterface.GetActiveBatchCurrentStepResponse{

    private final static String TAG = "UseCaseGetActiveBatchCurrentStep";

    private final MobileDeviceInterface device;
    private final Response response;

    public UseCaseGetActiveBatchCurrentStep(MobileDeviceInterface device, Response response){
        this.device = device;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (device.getUser().isSignedIn()) {
            //// stop monitoring demo offers, personal offers, public offers, and scheduled batches
            Timber.tag(TAG).d("...device has signed in user, get current active batch step");
            device.getCloudActiveBatch().getActiveBatchCurrentStepRequest(device.getUser().getDriver(), this);

        } else {
            // do nothing
            Timber.tag(TAG).d("...there is no signed in user, do nothing");
            response.useCaseGetActiveBatchCurrentStepFailure();
        }
    }

    //// GetActiveBatchCurrentStepRequest response interface
    public void cloudGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep){
        Timber.tag(TAG).d("cloudGetActiveBatchCurrentStepSuccess");
        response.useCaseGetActiveBatchCurrentStepSuccess(batchDetail, serviceOrder, orderStep);
    }

    public void cloudGetActiveBatchCurrentStepFailure(){
        Timber.tag(TAG).d("cloudGetActiveBatchCurrentStepFailure");
        response.useCaseGetActiveBatchCurrentStepFailure();
    }

    public interface Response {
        void useCaseGetActiveBatchCurrentStepSuccess(BatchDetail batchDetail, ServiceOrder serviceOrder, OrderStepInterface orderStep);

        void useCaseGetActiveBatchCurrentStepFailure();
    }
}
