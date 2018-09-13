/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.authorizePaymentStep;

import android.graphics.Bitmap;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudImageDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.ReceiptRequest;
import timber.log.Timber;

/**
 * Created on 9/8/2018
 * Project : Driver
 */
public class UseCaseReceiptDetectImageText implements
        Runnable,
        DeviceImageStorageInterface.DeleteResponse,
        DeviceImageStorageInterface.GetResponse,
        CloudActiveBatchInterface.ReceiptRequestDeviceAbsoluteFileNameReponse,
        CloudImageDetectionInterface.DetectImageTextResponse {

    private final static String TAG = "UseCaseReceiptDetectImageText";

    private final MobileDeviceInterface device;
    private final String imageDeviceAbsoluteFilename;
    private final Response response;

    private ReceiptRequest receiptRequest;


    public UseCaseReceiptDetectImageText(MobileDeviceInterface device, String imageDeviceAbsoluteFilename, ReceiptRequest receiptRequest, Response response){
        this.device = device;
        this.imageDeviceAbsoluteFilename = imageDeviceAbsoluteFilename;
        this.receiptRequest = receiptRequest;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (receiptRequest.getHasDeviceFile()){
            //if this receiptRequest already has a device file, we need to delete this old file and save the new file name to the receiptRequest
            Timber.tag(TAG).d("...deleting OLD image file -> " + receiptRequest.getDeviceAbsoluteFileName());
            device.getDeviceImageStorage().deleteImageRequest(receiptRequest.getDeviceAbsoluteFileName(), this);

        } else {
            //put this filename into the photoRequest & detect image label
            Timber.tag(TAG).d("...no prior image file, go straight to label detection");
            detectImageText();
        }
    }

    /// response interface for deleteImageRequest
    public void deviceImageStorageDeleteComplete(){
        Timber.tag(TAG).d("...deviceImageStorageDeleteComplete, detecting image label");
        detectImageText();
    }

    private void detectImageText(){
        //now put new file name into receipt request object
        receiptRequest.setDeviceAbsoluteFileName(imageDeviceAbsoluteFilename);
        receiptRequest.setHasDeviceFile(true);

        //see if we need to detect the image on this photoRequest
        if (receiptRequest.getDoTextRecognition()) {
            Timber.tag(TAG).d("...starting text detection");
            //first, let's get the bitmap of the image
            device.getDeviceImageStorage().getImageRequest(imageDeviceAbsoluteFilename, this);

        } else {
            Timber.tag(TAG).d("...skipping text detection");
            //now update the photoRequest object in the cloud
            receiptRequest.setHasTextMap(false);
            receiptRequest.setTextMap(null);
            device.getCloudActiveBatch().updateReceiptRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), receiptRequest, this);
        }
    }

    //// response interface for deviceImageStorageGet
    public void deviceImageStorageGetSuccess(Bitmap bitmap){
        Timber.tag(TAG).d("deviceImageStorageGetSuccess");
        /// now we can do a label detection on this image
        //first, let's detect the image
        device.getCloudImageDetection().detectImageTextRequest(bitmap, this);
    }

    public void deviceImageStorageGetFailure(){
        Timber.tag(TAG).w("deviceImageStorageGetFailure");
        // we weren't able to read this file, something went wrong
        //now update the photoRequest object in the cloud
        receiptRequest.setHasTextMap(false);
        receiptRequest.setTextMap(null);
        device.getCloudActiveBatch().updateReceiptRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), receiptRequest, this);

    }

    //// image detection response interface
    public void detectImageTextSuccess(HashMap<String, String> textMap){
        Timber.tag(TAG).d("detectImageTextSuccess");
        //we did get an image detect success
        //now update the receiptRequest in the cloud
        Timber.tag(TAG).d( "  ...textMap size = %d", textMap.size());

        if (textMap.size() == 0) {
            receiptRequest.setHasTextMap(false);
            receiptRequest.setTextMap(null);
        } else {
            receiptRequest.setHasTextMap(true);
            receiptRequest.setTextMap(textMap);
        }

        device.getCloudActiveBatch().updateReceiptRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), receiptRequest, this);
    }

    public void detectImageTextFailure(){
        Timber.tag(TAG).w("detectImageTextFailure");
        //we didn't get an image detect success
        //how update the receipt request in the cloud
        receiptRequest.setHasTextMap(false);
        receiptRequest.setTextMap(null);
        device.getCloudActiveBatch().updateReceiptRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), receiptRequest, this);
    }

    ///updateReceiptRequest response interface
    public void cloudActiveBatchUpdateReceiptRequestDeviceAbsoluteFilenameComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdateReceiptRequestDeviceAbsoluteFilenameComplete");
        response.receiptDetectImageTextComplete();
    }

    public interface Response {
        void receiptDetectImageTextComplete();
    }

}
