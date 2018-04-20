/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.manageBatch.forfeitBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class UseCaseForfeitBatchRequest implements
        Runnable,
        CloudScheduledBatchInterface.BatchForfeitResponse {

    private static final String TAG = "UseCaseForfeitBatchRequest";

    private final CloudScheduledBatchInterface cloudBatch;
    private final Driver driver;
    private final String batchGuid;
    private final UseCaseForfeitBatchRequest.Response response;

    public UseCaseForfeitBatchRequest(MobileDeviceInterface device, String batchGuid, UseCaseForfeitBatchRequest.Response response){
        this.cloudBatch = device.getCloudScheduledBatch();
        this.driver = device.getUser().getDriver();
        this.batchGuid = batchGuid;
        this.response = response;

    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        Timber.tag(TAG).d("   ...batchGuid -> " + batchGuid);
        cloudBatch.batchForfeitRequest(driver, batchGuid, this);
    }


    public void cloudScheduledBatchForfeitSuccess(String batchGuid){
        Timber.tag(TAG).d("   ...cloudScheduledBatchForfeitSuccess");
        response.useCaseForfeitBatchSuccess(batchGuid);
    }

    public void cloudScheduledBatchForfeitFailure(String batchGuid){
        Timber.tag(TAG).d("   ...cloudScheduledBatchForfeitFailure");
        response.useCaseForfeitBatchFailure(batchGuid);
    }

    public void cloudScheduledBatchForfeitTimeout(String batchGuid){
        Timber.tag(TAG).d("   ...cloudScheduledBatchForfeitTimeout");
        response.useCaseForfeitBatchTimeout(batchGuid);
    }

    public void cloudScheduledBatchForfeitDenied(String batchGuid, String reason){
        Timber.tag(TAG).d("   ...cloudScheduledBatchForfeitDenied");
        response.useCaseForfeitBatchDenied(batchGuid, reason);
    }

    public interface Response {
        void useCaseForfeitBatchSuccess(String batchGuid);

        void useCaseForfeitBatchFailure(String batchGuid);

        void useCaseForfeitBatchTimeout(String batchGuid);

        void useCaseForfeitBatchDenied(String batchGuid, String reason);
    }
}
