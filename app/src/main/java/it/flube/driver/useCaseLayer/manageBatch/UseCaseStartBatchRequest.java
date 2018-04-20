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
        cloudScheduledBatch.startScheduledBatchRequest(driver, batchGuid, this );
    }

    public void cloudStartScheduledBatchComplete(){
        Timber.tag(TAG).d("   ...cloudStartScheduledBatchComplete");
        cloudActiveBatch.startActiveBatchRequest(driver, batchGuid, ActiveBatchManageInterface.ActorType.MOBILE_USER, this);
    }

    public void cloudStartActiveBatchComplete(){
        Timber.tag(TAG).d("   ...cloudStartActiveBatchComplete");
        response.useCaseStartBatchComplete(batchGuid);
    }

    public interface Response {
        void useCaseStartBatchComplete(String batchGuid);
    }
}
