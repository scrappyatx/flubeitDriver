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

    private final MobileDeviceInterface device;
    private final String batchGuid;
    private final String orderStepGuid;
    private final String photoRequestGuid;
    private final Response response;

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
            device.getCloudActiveBatch().getActiveBatchPhotoRequestRequest(device.getCloudAuth().getDriver(),batchGuid, orderStepGuid, photoRequestGuid, this);
        } else {
            // do nothing
            Timber.tag(TAG).d("...there is no signed in user, do nothing");
            response.useCaseGetActiveBatchPhotoRequestFailure();
        }
    }

    public void cloudGetActiveBatchPhotoRequestSuccess(PhotoRequest photoRequest){
        Timber.tag(TAG).d("...cloudGetActiveBatchPhotoRequestSuccess");
        response.useCaseGetActiveBatchPhotoRequestSuccess(photoRequest);
    }


    public void cloudGetActiveBatchPhotoRequestFailure(){
        Timber.tag(TAG).d("...cloudGetActiveBatchPhotoRequestFailure");
        response.useCaseGetActiveBatchPhotoRequestFailure();
    }

    public interface Response {
        void useCaseGetActiveBatchPhotoRequestSuccess(PhotoRequest photoRequest);

        void useCaseGetActiveBatchPhotoRequestFailure();
    }

}
