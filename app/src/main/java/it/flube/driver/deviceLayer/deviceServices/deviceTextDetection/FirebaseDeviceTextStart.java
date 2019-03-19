/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceTextDetection;

import android.graphics.Bitmap;

import it.flube.driver.deviceLayer.cloudServices.cloudTextDetection.FirebaseCloudTextDetection;
import it.flube.driver.modelLayer.interfaces.CloudTextDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceTextDetectionInterface;
import timber.log.Timber;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class FirebaseDeviceTextStart implements
        DeviceImageStorageInterface.GetResponse {
    private static final String TAG = "FirebaseDeviceTextStart";

    private DeviceTextDetectionInterface.DeviceDetectImageTextResponse response;

    public void textDetectionStart(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, DeviceTextDetectionInterface.DeviceDetectImageTextResponse response) {
        Timber.tag(TAG).d("textDetectionStart");
        Timber.tag(TAG).d("   imageAbsoluteFilename -> %s", imageAbsoluteFilename);
        this.response = response;
        //load the image file into a bitmap
        deviceImageStorage.getImageRequest(imageAbsoluteFilename, this);
    }


    //// response interface for deviceImageStorageGet
    public void deviceImageStorageGetSuccess(Bitmap bitmap) {
        Timber.tag(TAG).d("deviceImageStorageGetSuccess");
        ///now we can do cloud image label detection on this bitmap
        new FirebaseDeviceTextDetection().detectTextRequest(bitmap, response);
    }

    public void deviceImageStorageGetFailure() {
        Timber.tag(TAG).w("deviceImageStorageGetFailure");
        // we weren't able to read this file, something went wrong
        response.deviceDetectImageTextFailure();
        response = null;
    }
}


