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
        CloudImageStorageInterface.WaitForAllPendingUploadsToFinishResponse,
        CloudImageStorageInterface.WaitForAllPendingDeviceFileDeletesToFinishResponse {

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

        /// first, wait for all pending uploads to finish
        device.getCloudImageStorage().waitForAllPendingUploadsToFinish(driver, deviceInfo, batchGuid, this);
    }



    //// wait for pending uploads interface
    public void cloudImageStorageAllUploadsInProgress(Integer uploadsPending){
        Timber.tag(TAG).d("cloudImageStorageAllUploadsInProgress -> %s", Integer.toString(uploadsPending));
        //// this will fire to say how many uploads remain
        response.pendingUploadsRemaining(uploadsPending);
    }

    public void cloudImageStorageAllUploadsComplete(){
        Timber.tag(TAG).d("...cloudImageStorageAllUploadsComplete");

        /// once all the uploads are done, then delete all files
        Timber.tag(TAG).d("...cloudImageStorageStartOrResumeComplete");
        device.getCloudImageStorage().waitForAllPendingDeviceFilesToBeDeletedRequest(driver, deviceInfo, device.getDeviceImageStorage(),this);
    }

    //// wait for pending deletes interface
    public void cloudImageStorageDeviceFileDeletesInProgress(Integer deletesPending){
        Timber.tag(TAG).d("...cloudImageStorageAllUploadsComplete");
        /// this will fire to say how many files that need to be deleted remain
        response.pendingDeviceFileDeletesRemaining(deletesPending);
    }

    public void cloudImageStorageAllDeletesComplete(){
        Timber.tag(TAG).d("...cloudImageStorageAllUploadsComplete");
        //// and now we are ready to finish
        response.batchIsReadyToFinish(batchGuid);
    }


    public interface Response {
        void pendingUploadsRemaining(Integer uploadsPending);

        void pendingDeviceFileDeletesRemaining(Integer deletesPending);

        void batchIsReadyToFinish(String batchGuid);
    }
}
