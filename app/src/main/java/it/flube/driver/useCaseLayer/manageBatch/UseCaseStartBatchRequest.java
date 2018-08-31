/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class UseCaseStartBatchRequest implements
    Runnable,
    CloudScheduledBatchInterface.StartScheduledBatchResponse,
    CloudActiveBatchInterface.StartActiveBatchResponse {

    private static final String TAG = "UseCaseStartBatchRequest";

    private final CloudActiveBatchInterface cloudActiveBatch;
    private final CloudScheduledBatchInterface cloudScheduledBatch;
    private final Driver driver;
    private final String batchGuid;
    private final Response response;

    public UseCaseStartBatchRequest(MobileDeviceInterface device, String batchGuid, Response response){
        this.cloudActiveBatch = device.getCloudActiveBatch();
        this.cloudScheduledBatch = device.getCloudScheduledBatch();
        this.driver = device.getUser().getDriver();
        this.batchGuid = batchGuid;
        this.response = response;

    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        Timber.tag(TAG).d("   ...batchGuid -> " + batchGuid);

        Timber.tag(TAG).d("   ...making request to start active batch...");
        cloudActiveBatch.startActiveBatchRequest(driver, batchGuid, ActiveBatchManageInterface.ActorType.MOBILE_USER, this);
    }



    //// SUCCESS callback from active batch start request, now remove the batch from the scheduled batch list
    public void cloudStartActiveBatchSuccess(String batchGuid, String driverProxyDialNumber, String driverProxyDisplayNumber) {
        Timber.tag(TAG).d("   ...cloudStartActiveBatchSuccess");
        Timber.tag(TAG).d("   ...now removing batch from scheduled batch list");

        //// now remove the batch from the scheduled batch list
        cloudScheduledBatch.startScheduledBatchRequest(driver, batchGuid, this );
    }

    /// callback from removing batch from scheduled batch list, we are done
    public void cloudStartScheduledBatchComplete(){
        Timber.tag(TAG).d("   ...cloudStartScheduledBatchComplete");
        response.useCaseStartBatchSuccess(batchGuid);
    }

    ///// FAILURE callbacks from active batch start request

    public void cloudStartActiveBatchFailure(String batchGuid) {
        Timber.tag(TAG).d("   ...cloudStartActiveBatchFailure");
        response.useCaseStartBatchFailure(batchGuid);
    }

    public void cloudStartActiveBatchTimeout(String batchGuid){
        Timber.tag(TAG).d("   ...cloudStartActiveBatchTimeout");
        response.useCaseStartBatchTimeout(batchGuid);
    }

    public void cloudStartActiveBatchDenied(String batchGuid, String reason){
        Timber.tag(TAG).d("   ...cloudStartActiveBatchDenied, reason = " + reason);
        response.useCaseStartBatchDenied(batchGuid, reason);
    }

    public interface Response {
        void useCaseStartBatchSuccess(String batchGuid);
        void useCaseStartBatchFailure(String batchGuid);
        void useCaseStartBatchTimeout(String batchGuid);
        void useCaseStartBatchDenied(String batchGuid, String reason);
    }
}
