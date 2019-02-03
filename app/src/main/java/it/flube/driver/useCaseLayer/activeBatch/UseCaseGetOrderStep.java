/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 5/6/2018
 * Project : Driver
 */
public class UseCaseGetOrderStep implements
        Runnable,
        CloudActiveBatchInterface.GetActiveBatchStepResponse {

    private final static String TAG = "UseCaseGetOrderStep";

    private MobileDeviceInterface device;
    private String batchGuid;
    private String stepGuid;
    private Response response;

    public UseCaseGetOrderStep(MobileDeviceInterface device, String batchGuid, String stepGuid, Response response){
        this.device = device;
        this.batchGuid = batchGuid;
        this.stepGuid = stepGuid;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        device.getCloudActiveBatch().getActiveBatchStepRequest(device.getCloudAuth().getDriver(), batchGuid, stepGuid, this);
    }

    /// response interface for GetActiveBatchStepRequest
    public void cloudGetActiveBatchStepSuccess(OrderStepInterface orderStep){
        Timber.tag(TAG).d("cloudGetActiveBatchStepSuccess");
        response.getOrderStepSuccess(orderStep);
        close();
    }

    public void cloudGetActiveBatchStepFailure(){
        Timber.tag(TAG).d("cloudGetActiveBatchStepFailure");
        response.getOrderStepFailure();
        close();
    }

    public void close(){
        Timber.tag(TAG).d("close");
        device = null;
        batchGuid = null;
        stepGuid = null;
        response = null;
    }

    public interface Response {
        void getOrderStepSuccess(OrderStepInterface orderStep);

        void getOrderStepFailure();
    }
}
