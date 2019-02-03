/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.activeBatch;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 4/29/2018
 * Project : Driver
 */
public class UseCaseGetActiveBatchPhotoRequest implements
        Runnable,
        CloudActiveBatchInterface.GetActiveBatchPhotoRequestResponse {

    private final static String TAG = "UseCaseGetActiveBatchPhotoRequest";

    private MobileDeviceInterface device;
    private String batchGuid;
    private String orderStepGuid;
    private String photoRequestGuid;
    private Response response;
    private Driver driver;

    public UseCaseGetActiveBatchPhotoRequest(MobileDeviceInterface device, String batchGuid, String orderStepGuid, String photoRequestGuid, Response response){
        this.device = device;
        this.batchGuid = batchGuid;
        this.orderStepGuid = orderStepGuid;
        this.photoRequestGuid = photoRequestGuid;
        this.response = response;
    }

    public void run() {
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (device.getCloudAuth().hasDriver()) {
            Timber.tag(TAG).d("...getting photo request");
            this.driver = device.getCloudAuth().getDriver();
            device.getCloudActiveBatch().getActiveBatchPhotoRequestRequest(device.getCloudAuth().getDriver(),batchGuid, orderStepGuid, photoRequestGuid, this);
        } else {
            // do nothing
            Timber.tag(TAG).d("...there is no signed in user, do nothing");
            response.useCaseGetActiveBatchPhotoRequestFailureNoDriver();
            close();
        }
    }

    /// interface for GetActiveBatchPhotoRequest response
    public void cloudGetActiveBatchPhotoRequestSuccess(PhotoRequest photoRequest){
        Timber.tag(TAG).d("...cloudGetActiveBatchPhotoRequestSuccess");
        response.useCaseGetActiveBatchPhotoRequestSuccess(driver, photoRequest);
        close();
    }


    public void cloudGetActiveBatchPhotoRequestFailure(){
        Timber.tag(TAG).d("...cloudGetActiveBatchPhotoRequestFailure");
        response.useCaseGetActiveBatchPhotoRequestFailureDriverButNoPhotoRequest(driver);
        close();
    }

    private void close(){
        Timber.tag(TAG).d("close");
        response = null;
        device = null;
        batchGuid = null;
        orderStepGuid = null;
        photoRequestGuid = null;

    }

    public interface Response {
        void useCaseGetActiveBatchPhotoRequestSuccess(Driver driver, PhotoRequest photoRequest);

        void useCaseGetActiveBatchPhotoRequestFailureDriverButNoPhotoRequest(Driver driver);

        void useCaseGetActiveBatchPhotoRequestFailureNoDriver();

    }

}
