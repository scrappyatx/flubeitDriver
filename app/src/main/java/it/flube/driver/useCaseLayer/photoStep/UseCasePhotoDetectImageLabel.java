/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.photoStep;

import android.graphics.Bitmap;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.ImageLabel;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 9/8/2018
 * Project : Driver
 */
public class UseCasePhotoDetectImageLabel implements
        Runnable,
        DeviceImageStorageInterface.DeleteResponse,
        DeviceImageStorageInterface.GetResponse,
        CloudActiveBatchInterface.PhotoRequestDeviceAbsoluteFileNameResponse,
        CloudImageDetectionInterface.DetectImageLabelResponse {


    private final static String TAG = "UseCasePhotoDetectImageLabel";

    private final MobileDeviceInterface device;
    private final String imageDeviceAbsoluteFilename;
    private final PhotoRequest photoRequest;
    private final Response response;

    public UseCasePhotoDetectImageLabel(MobileDeviceInterface device, String imageDeviceAbsoluteFilename, PhotoRequest photoRequest, Response response){
        this.device = device;
        this.imageDeviceAbsoluteFilename = imageDeviceAbsoluteFilename;
        this.photoRequest=  photoRequest;
        this.response = response;

    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (photoRequest.getHasDeviceFile()){
            //if this photoRequest already has a device file, we need to delete this old file and save the new file name to the photoRequest
            Timber.tag(TAG).d("...deleting OLD image file -> " + photoRequest.getDeviceAbsoluteFileName());
            device.getDeviceImageStorage().deleteImageRequest(photoRequest.getDeviceAbsoluteFileName(), this);
        } else {
            //put this filename into the photoRequest & detect image label
            Timber.tag(TAG).d("...no prior image file, go straight to label detection");
            photoRequest.setDeviceAbsoluteFileName(imageDeviceAbsoluteFilename);
            photoRequest.setHasDeviceFile(true);
            detectImageLabel();
        }

    }

    /// response interface for deleteImageRequest
    public void deviceImageStorageDeleteComplete(){
        //NOW do a detectImageLabel on the image
        Timber.tag(TAG).d("...deviceImageStorageDeleteComplete, detecting image label");
        detectImageLabel();
    }

    //// once old photo is deleted, we can detect label
    private void detectImageLabel(){
        //see if we need to detect the image on this photoRequest
        if (photoRequest.getDoDeviceImageDetection()) {
            Timber.tag(TAG).d("...starting image detection");
            //first, let's get the bitmap of the image
            device.getDeviceImageStorage().getImageRequest(imageDeviceAbsoluteFilename, this);

        } else {
            Timber.tag(TAG).d("...skipping image detection");
            //now update the photoRequest object in the cloud
            photoRequest.setHasLabelMap(false);
            photoRequest.setLabelMap(null);
            device.getCloudActiveBatch().updatePhotoRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), photoRequest, imageDeviceAbsoluteFilename, true, this);
        }
    }

    //// response interface for deviceImageStorageGet
    public void deviceImageStorageGetSuccess(Bitmap bitmap){
        Timber.tag(TAG).d("deviceImageStorageGetSuccess");
        /// now we can do a label detection on this image
        //first, let's detect the image
        device.getCloudImageDetection().detectImageLabelRequest(bitmap, this);
    }

    public void deviceImageStorageGetFailure(){
        Timber.tag(TAG).w("deviceImageStorageGetFailure");
        // we weren't able to read this file, something went wrong
        //now update the photoRequest object in the cloud
        photoRequest.setHasLabelMap(false);
        photoRequest.setLabelMap(null);
        device.getCloudActiveBatch().updatePhotoRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), photoRequest, null, false, this);

    }
    /// response interface for detectImageLabelRequest
    ///
    public void detectImageLabelSuccess(HashMap<String, ImageLabel> labelMap){
        Timber.tag(TAG).d("detectImageLabelSuccess");
        //we did get an image detect success
        //now update the photoRequest in the cloud
        photoRequest.setHasLabelMap(true);
        photoRequest.setLabelMap(labelMap);
        device.getCloudActiveBatch().updatePhotoRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), photoRequest, imageDeviceAbsoluteFilename, true, this);
    }

    public void detectImageLabelFailure(){
        Timber.tag(TAG).d("detectImageLabelFailure");
        //we didn't get an image detect success
        //how update the photoRequest in the cloud
        photoRequest.setHasLabelMap(false);
        photoRequest.setLabelMap(null);
        device.getCloudActiveBatch().updatePhotoRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), photoRequest, imageDeviceAbsoluteFilename, true, this);
    }

    /// response interface for updating photoRequest with file name for device image, and with results of label detection
    public void cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete");
        response.photoDetectImageLabelComplete();
    }


    public interface Response {
        void photoDetectImageLabelComplete();
    }

}
