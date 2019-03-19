/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageDetection;

import android.graphics.Bitmap;

import it.flube.driver.deviceLayer.deviceServices.deviceImageDetection.FirebaseDeviceImageLabelDetection;
import it.flube.driver.deviceLayer.deviceServices.imageLabelAnalysis.ImageLabelAnalysis;
import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.libbatchdata.entities.ImageDetectionResults;
import it.flube.libbatchdata.entities.ImageMatchResults;
import it.flube.libbatchdata.entities.ImageMatchSettings;
import timber.log.Timber;

/**
 * Created on 3/6/2019
 * Project : Driver
 */
public class FirebaseCloudImageMatchStart implements
        DeviceImageStorageInterface.GetResponse,
        CloudImageDetectionInterface.CloudDetectImageLabelResponse,
        ImageLabelAnalysis.Response {
    public static final String TAG = "FirebaseDeviceImageMatchStart";

    CloudImageDetectionInterface.CloudMatchImageLabelResponse response;
    ImageMatchSettings settings;

    public void imageMatchRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, ImageMatchSettings settings, CloudImageDetectionInterface.CloudMatchImageLabelResponse responsee) {
        Timber.tag(TAG).d("imageMatchRequest");
        Timber.tag(TAG).d("   imageAbsoluteFilename -> %s", imageAbsoluteFilename);
        this.response = response;
        this.settings = settings;
        //load the image file into a bitmap
        deviceImageStorage.getImageRequest(imageAbsoluteFilename, this);
    }

    private void close(){
        response = null;
        settings = null;
    }

    //// response interface for deviceImageStorageGet
    public void deviceImageStorageGetSuccess(Bitmap bitmap) {
        Timber.tag(TAG).d("deviceImageStorageGetSuccess");
        ///now we can do cloud image label detection on this bitmap
        new FirebaseCloudImageLabelDetection().detectImageRequest(bitmap, this);
    }

    public void deviceImageStorageGetFailure() {
        Timber.tag(TAG).w("deviceImageStorageGetFailure");
        // we weren't able to read this file, something went wrong
        response.cloudMatchImageLabelFailure();
        close();
    }

    //// response interface for cloudDetectImageLabelResponse
    public void cloudDetectImageLabelSuccess(ImageDetectionResults imageDetectionResults){
        Timber.tag(TAG).d("cloudDetectImageLabelSuccess");
        // now we can see if the results match the settings
        new ImageLabelAnalysis().analyzeImageRequest(imageDetectionResults, settings, this);
    }

    public void cloudDetectImageLabelFailure(){
        Timber.tag(TAG).d("deviceDetectImageLabelFailure");
        response.cloudMatchImageLabelFailure();
        close();
    }

    /// response interface for ImageLabelAnalysis
    public void analyzeImageComplete(ImageMatchResults results){
        Timber.tag(TAG).d("analyzeImageComplete");
        response.cloudMatchImageLabelSuccess(results);
        close();
    }

}
