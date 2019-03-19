/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.authorizePaymentStep;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.ReceiptRequest;
import timber.log.Timber;

/**
 * Created on 3/17/2019
 * Project : Driver
 */
public class UseCaseReceiptImageDeviceAbsoluteFilename implements
        Runnable,
        DeviceImageStorageInterface.DeleteResponse,
        CloudActiveBatchInterface.ReceiptRequestDeviceAbsoluteFileNameResponse {

    private final static String TAG = "UseCaseReceiptImageDeviceAbsoluteFilename";

    private MobileDeviceInterface device;
    private String imageDeviceAbsoluteFilename;
    private Response response;

    private ReceiptRequest receiptRequest;

    public UseCaseReceiptImageDeviceAbsoluteFilename(MobileDeviceInterface device, String imageDeviceAbsoluteFilename, ReceiptRequest receiptRequest, Response response){
        Timber.tag(TAG).d("UseCaseReceiptOcr");
        this.device = device;
        this.imageDeviceAbsoluteFilename = imageDeviceAbsoluteFilename;
        this.receiptRequest = receiptRequest;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        if (receiptRequest.getHasDeviceFile()){
            if (receiptRequest.getDeviceAbsoluteFileName().equals(imageDeviceAbsoluteFilename)){
                //this is the same image file, do nothing
                Timber.tag(TAG).d("...this is the same image file, do nothing");
                response.useCaseReceiptImageDeviceAbsoluteFilenameComplete(receiptRequest);
                close();
            } else {
                // this receipt request already has a device file, we need to delete this old file and save the new file to the receipt request
                Timber.tag(TAG).d("...different image file, deleting the OLD one -> " + receiptRequest.getDeviceAbsoluteFileName());
                device.getDeviceImageStorage().deleteImageRequest(receiptRequest.getDeviceAbsoluteFileName(), this);
            }
        } else {
            //put this filename into the photoRequest & detect image label
            Timber.tag(TAG).d("...no prior image file, update the receipt request");
            updateReceiptRequest();
        }
    }

    /// response interface for deleteImageRequest
    public void deviceImageStorageDeleteComplete(){
        Timber.tag(TAG).d("deviceImageStorageDeleteComplete");
        updateReceiptRequest();
    }

    private void updateReceiptRequest(){
        Timber.tag(TAG).d("updateReceiptRequest with this file -> %s", imageDeviceAbsoluteFilename);
        //now put new file name into receipt request object
        receiptRequest.setDeviceAbsoluteFileName(imageDeviceAbsoluteFilename);
        receiptRequest.setHasDeviceFile(true);
        device.getCloudActiveBatch().updateReceiptRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), receiptRequest, this);
    }

    private void close(){
        device = null;
        imageDeviceAbsoluteFilename=null;
        receiptRequest = null;
        response = null;
    }

    ///updateReceiptRequest response interface
    public void cloudActiveBatchUpdateReceiptRequestDeviceAbsoluteFilenameComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdateReceiptRequestDeviceAbsoluteFilenameComplete");
        response.useCaseReceiptImageDeviceAbsoluteFilenameComplete(receiptRequest);
        close();
    }

    public interface Response {
        void useCaseReceiptImageDeviceAbsoluteFilenameComplete(ReceiptRequest receiptRequest);
    }

}
