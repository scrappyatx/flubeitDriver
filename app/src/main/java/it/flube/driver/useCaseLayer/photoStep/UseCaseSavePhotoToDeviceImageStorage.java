/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.photoStep;

import android.graphics.Bitmap;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.account.UseCaseGetAccountDetails;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class UseCaseSavePhotoToDeviceImageStorage implements
        Runnable,
        DeviceImageStorageInterface.SaveResponse {

    private final static String TAG = "UseCaseSavePhotoToDeviceImageStorage";

    private final MobileDeviceInterface device;
    private final String imageGuid;
    private final Bitmap bitmap;
    private final Response response;

    public UseCaseSavePhotoToDeviceImageStorage(MobileDeviceInterface device, String imageGuid, Bitmap bitmap, Response response) {
        this.device = device;
        this.imageGuid = imageGuid;
        this.bitmap = bitmap;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        device.getDeviceImageStorage().saveImageRequest(imageGuid, bitmap, this);
    }

    public void deviceImageStorageSaveSuccess(String absoluteFileName) {
        response.imageSavedSuccess(imageGuid);
    }

    public void deviceImageStorageSaveFailure() {
        response.imageSavedFailure(imageGuid);
    }

    public interface Response {
        void imageSavedSuccess(String imageGuid);

        void imageSavedFailure(String imageGuid);
    }
}
