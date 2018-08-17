/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageDetection;

import android.graphics.Bitmap;

import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import timber.log.Timber;

/**
 * Created on 8/9/2018
 * Project : Driver
 */
public class ImageDetectionFirebaseWrapper implements CloudImageDetectionInterface {
    public static final String TAG="ImageDetectionFirebaseWrapper";

    public void detectImageLabelRequest(Bitmap bitmap, DetectImageLabelResponse response){
        Timber.tag(TAG).d("detectImageLabelRequest");
        new FirebaseImageLabelDetection().detectImageRequest(bitmap, response);
    }

    public void detectImageTextRequest(Bitmap bitmap, DetectImageTextResponse response){
        Timber.tag(TAG).d("detectImageTextRequest");
        new FirebaseImageTextDetection().detectImageRequest(bitmap, response);
    }

}
