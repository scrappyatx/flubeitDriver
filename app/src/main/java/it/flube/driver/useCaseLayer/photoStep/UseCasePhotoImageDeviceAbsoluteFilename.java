/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.photoStep;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.authorizePaymentStep.UseCaseReceiptImageDeviceAbsoluteFilename;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.ReceiptRequest;
import timber.log.Timber;

/**
 * Created on 3/19/2019
 * Project : Driver
 */
public class UseCasePhotoImageDeviceAbsoluteFilename implements
        Runnable,
        DeviceImageStorageInterface.DeleteResponse,
        CloudActiveBatchInterface.PhotoRequestDeviceAbsoluteFileNameResponse {

    private final static String TAG = "UseCaseReceiptImageDeviceAbsoluteFilename";

    private MobileDeviceInterface device;
    private String imageDeviceAbsoluteFilename;
    private Response response;
    private PhotoRequest photoRequest;

    public UseCasePhotoImageDeviceAbsoluteFilename(MobileDeviceInterface device, String imageDeviceAbsoluteFilename, PhotoRequest photoRequest,Response response){
        Timber.tag(TAG).d("UseCasePhotoImageDeviceAbsoluteFilename");
        this.device = device;
        this.imageDeviceAbsoluteFilename = imageDeviceAbsoluteFilename;
        this.photoRequest = photoRequest;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        if (photoRequest.getHasDeviceFile()){
            if (photoRequest.getDeviceAbsoluteFileName().equals(imageDeviceAbsoluteFilename)){
                //this is the same image file, do nothing
                Timber.tag(TAG).d("...this is the same image file, do nothing");
                response.useCasePhotoRequestImageDeviceAbsoluteFilenameComplete(photoRequest);
                close();
            } else {
                // this receipt request already has a device file, we need to delete this old file and save the new file to the receipt request
                Timber.tag(TAG).d("...different image file, deleting the OLD one -> " + photoRequest.getDeviceAbsoluteFileName());
                device.getDeviceImageStorage().deleteImageRequest(photoRequest.getDeviceAbsoluteFileName(), this);
            }
        } else {
            //put this filename into the photoRequest
            Timber.tag(TAG).d("...no prior image file, update the receipt request");
            updatePhotoRequest();
        }
    }

    private void close(){
        Timber.tag(TAG).d("close");
        device = null;
        imageDeviceAbsoluteFilename=null;
        photoRequest = null;
        response = null;
    }

    /// response interface for deleteImageRequest
    public void deviceImageStorageDeleteComplete(){
        Timber.tag(TAG).d("deviceImageStorageDeleteComplete");
        updatePhotoRequest();
    }

    private void updatePhotoRequest(){
        Timber.tag(TAG).d("updateReceiptRequest with this file -> %s", imageDeviceAbsoluteFilename);
        //now put new file name into receipt request object
        photoRequest.setDeviceAbsoluteFileName(imageDeviceAbsoluteFilename);
        photoRequest.setHasDeviceFile(true);
        device.getCloudActiveBatch().updatePhotoRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), photoRequest, this);
    }

    ///updatePhotoRequest response interface
    public void cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete");
        response.useCasePhotoRequestImageDeviceAbsoluteFilenameComplete(photoRequest);
        close();
    }

    public interface Response {
        void useCasePhotoRequestImageDeviceAbsoluteFilenameComplete(PhotoRequest photoRequest);
    }

}
