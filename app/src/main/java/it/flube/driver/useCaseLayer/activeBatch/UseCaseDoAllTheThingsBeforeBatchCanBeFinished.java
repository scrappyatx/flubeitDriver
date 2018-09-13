/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 6/22/2018
 * Project : Driver
 */
public class UseCaseDoAllTheThingsBeforeBatchCanBeFinished implements
    Runnable,
        CloudImageStorageInterface.StartOrResumeResponse,
        CloudImageStorageInterface.WaitForUploadToFinishResponse {

    private final static String TAG = "UseCaseDoAllTheThingsBeforeBatchCanBeFinished";

    private final MobileDeviceInterface device;
    private final Driver driver;
    private final DeviceInfo deviceInfo;
    private final String batchGuid;
    private final Response response;

    public UseCaseDoAllTheThingsBeforeBatchCanBeFinished(MobileDeviceInterface device, String batchGuid, Response response){
        this.device = device;
        this.driver = device.getCloudAuth().getDriver();
        this.deviceInfo = device.getDeviceInfo();
        this.batchGuid = batchGuid;
        this.response = response;
    }

    public void run(){
        //// do all the things here that need to be done before the batch can be completed (uploading files, etc)
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        device.getCloudImageStorage().startOrResumeUploadingImagesForActiveBatch(driver,deviceInfo,batchGuid, this);
    }

    public void cloudImageStorageStartOrResumeComplete(){
        Timber.tag(TAG).d("...cloudImageStorageStartOrResumeComplete");
        device.getCloudImageStorage().waitForAllImageFilesForActiveBatchToFinishUploading(driver, deviceInfo, batchGuid, this);
    }

    public void cloudImageStorageAllUploadsComplete(){
        Timber.tag(TAG).d("...cloudImageStorageAllUploadsComplete");
        response.batchIsReadyToFinish(batchGuid);
    }

    public interface Response {
        void batchIsReadyToFinish(String batchGuid);
    }
}
