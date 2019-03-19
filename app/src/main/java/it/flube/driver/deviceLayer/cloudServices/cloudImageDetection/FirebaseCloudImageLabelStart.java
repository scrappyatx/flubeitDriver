/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageDetection;

import android.graphics.Bitmap;

import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;
import timber.log.Timber;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class FirebaseCloudImageLabelStart implements
        DeviceImageStorageInterface.GetResponse {
    public static final String TAG = "FirebaseCloudImageLabelStart";

    CloudImageDetectionInterface.CloudDetectImageLabelResponse response;

    public void imageLabelRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, CloudImageDetectionInterface.CloudDetectImageLabelResponse response){
        Timber.tag(TAG).d("imageLabelRequest");
        Timber.tag(TAG).d("   imageAbsoluteFilename -> %s",imageAbsoluteFilename);
        this.response = response;
        //load the image file into a bitmap
        deviceImageStorage.getImageRequest(imageAbsoluteFilename, this);
    }

    //// response interface for deviceImageStorageGet
    public void deviceImageStorageGetSuccess(Bitmap bitmap){
        Timber.tag(TAG).d("deviceImageStorageGetSuccess");
        ///now we can do cloud image label detection on this bitmap
        new FirebaseCloudImageLabelDetection().detectImageRequest(bitmap, response);
    }

    public void deviceImageStorageGetFailure(){
        Timber.tag(TAG).w("deviceImageStorageGetFailure");
        // we weren't able to read this file, something went wrong
        response.cloudDetectImageLabelFailure();
        response = null;
    }

}
