/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.photoStep;

import android.graphics.Bitmap;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.account.UseCaseGetAccountDetails;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class UseCaseSavePhotoToDeviceImageStorage implements
        Runnable,
        DeviceImageStorageInterface.DeleteResponse,
        DeviceImageStorageInterface.SaveResponse,
        CloudActiveBatchInterface.PhotoRequestDeviceAbsoluteFileNameResponse {

    private final static String TAG = "UseCaseSavePhotoToDeviceImageStorage";

    private final MobileDeviceInterface device;
    private final String imageGuid;
    private final Bitmap bitmap;
    private final Response response;
    private PhotoRequest photoRequest;

    private Boolean saveImageSuccess;

    public UseCaseSavePhotoToDeviceImageStorage(MobileDeviceInterface device, PhotoRequest photoRequest, String imageGuid, Bitmap bitmap, Response response) {
        this.device = device;
        this.imageGuid = imageGuid;
        this.bitmap = bitmap;
        this.response = response;
        this.photoRequest = photoRequest;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (photoRequest.getHasDeviceFile()) {
            /// if we already have an image saved to the device for this photoRequest, we want to delete it
            Timber.tag(TAG).d("...deleting OLD image file -> " + photoRequest.getDeviceAbsoluteFileName());
            device.getDeviceImageStorage().deleteImageRequest(photoRequest.getDeviceAbsoluteFileName(), this);
        } else {
            /// go ahead and save the new image
            Timber.tag(TAG).d("...no OLD image file to delete, saving new image");
            device.getDeviceImageStorage().saveImageRequest(imageGuid, bitmap, this);
        }
    }

    /// response interface for deleteImageRequest
    public void deviceImageStorageDeleteComplete(){
        //NOW we can save the new image
        Timber.tag(TAG).d("...deviceImageStorageDeleteComplete, saving new image");
        device.getDeviceImageStorage().saveImageRequest(imageGuid, bitmap, this);
    }

    /// response interface for saveImageRequest to device storage
    public void deviceImageStorageSaveSuccess(String absoluteFileName) {
        Timber.tag(TAG).d("deviceImageStorageSaveSuccess : absoluteFileName = " + absoluteFileName);
        saveImageSuccess = true;

        ///update our photoRequest object in the cloud to reflect we have saved an image on the device for this photo request

        device.getCloudActiveBatch().updatePhotoRequestDeviceAbsoluteFileNameRequest(device.getUser().getDriver(), photoRequest, absoluteFileName, true, this);
    }

    public void deviceImageStorageSaveFailure() {
        Timber.tag(TAG).d("deviceImageStorageSaveFailure");
        saveImageSuccess = false;

        ///update our photoRequest object in the cloud to reflect we DON'T have an image save on this device for this photo request
        device.getCloudActiveBatch().updatePhotoRequestDeviceAbsoluteFileNameRequest(device.getUser().getDriver(), photoRequest, null, false, this);

    }

    /// response interface for updating photoRequest with file name for device image
    public void cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete");

        if (saveImageSuccess){
            response.imageSavedSuccess(imageGuid);
        } else {
            response.imageSavedFailure(imageGuid);
        }
    }

    public interface Response {
        void imageSavedSuccess(String imageGuid);

        void imageSavedFailure(String imageGuid);
    }
}
