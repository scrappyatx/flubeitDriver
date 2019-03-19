/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceTextDetection;

import android.graphics.Bitmap;

import it.flube.driver.deviceLayer.cloudServices.cloudTextDetection.FirebaseCloudTextDetection;
import it.flube.driver.deviceLayer.deviceServices.receiptOcr.ReceiptOcrAnalysis;
import it.flube.driver.modelLayer.interfaces.CloudTextDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceTextDetectionInterface;
import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.TextDetectionResults;
import timber.log.Timber;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class FirebaseDeviceReceiptOcr implements
        DeviceImageStorageInterface.GetResponse,
        DeviceTextDetectionInterface.DeviceDetectImageTextResponse,
        ReceiptOcrAnalysis.Response {
    private static final String TAG = "FirebaseDeviceReceiptOcr";

    private DeviceTextDetectionInterface.DeviceDetectReceiptOcrResponse response;
    private ReceiptOcrSettings settings;

    public void receiptOcrRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, ReceiptOcrSettings receiptOcrSettings, DeviceTextDetectionInterface.DeviceDetectReceiptOcrResponse response){
        Timber.tag(TAG).d("receiptOcrRequest");
        this.response = response;
        this.settings = receiptOcrSettings;

        //first, do cloud text detection, to get a text detection results object
        //first, get the bitmap for the file
        deviceImageStorage.getImageRequest(imageAbsoluteFilename, this);
    }

    private void close(){
        Timber.tag(TAG).d("close");
        response = null;
        settings = null;
    }

    //// response interface for deviceImageStorageGet
    public void deviceImageStorageGetSuccess(Bitmap bitmap){
        Timber.tag(TAG).d("deviceImageStorageGetSuccess");
        ///now we can do cloud image label detection on this bitmap
        new FirebaseDeviceTextDetection().detectTextRequest(bitmap, this);
    }

    public void deviceImageStorageGetFailure(){
        Timber.tag(TAG).w("deviceImageStorageGetFailure");
        // we weren't able to read this file, something went wrong
        response.deviceDetectReceiptOcrFailure();
        close();
    }


    //// interface for DeviceDetectImageTextResponse
    public void deviceDetectImageTextSuccess(TextDetectionResults textDetectionResults){
        Timber.tag(TAG).d("deviceDetectImageTextSuccess");
        //second, do a receipt ocr analysis on the text detection object
        new ReceiptOcrAnalysis().analyzeReceiptRequest(textDetectionResults,settings, this);
    }

    public void deviceDetectImageTextFailure(){
        Timber.tag(TAG).d("deviceDetectImageTextFailure");
        //// didn't get a text detection object
        response.deviceDetectReceiptOcrFailure();
        close();
    }

    /// interface for ReceiptOcrAnalysis.Response
    public void analyzeReceiptComplete(ReceiptOcrResults receiptOcrResults){
        Timber.tag(TAG).d("analyzeReceiptComplete");
        response.deviceDetectReceiptOcrSuccess(receiptOcrResults);
        close();
    }

}
