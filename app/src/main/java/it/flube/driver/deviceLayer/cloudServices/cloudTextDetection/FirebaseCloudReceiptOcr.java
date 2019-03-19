/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudTextDetection;

import android.graphics.Bitmap;

import it.flube.driver.deviceLayer.deviceServices.receiptOcr.ReceiptOcrAnalysis;
import it.flube.driver.modelLayer.interfaces.CloudTextDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.TextDetectionResults;
import timber.log.Timber;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class FirebaseCloudReceiptOcr implements
        DeviceImageStorageInterface.GetResponse,
        CloudTextDetectionInterface.CloudDetectImageTextResponse,
        ReceiptOcrAnalysis.Response {
    private static final String TAG = "FirebaseCloudReceiptOcr";

    private CloudTextDetectionInterface.CloudDetectReceiptOcrResponse response;
    private ReceiptOcrSettings settings;

    public void receiptOcrRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, ReceiptOcrSettings receiptOcrSettings, CloudTextDetectionInterface.CloudDetectReceiptOcrResponse response){
        Timber.tag(TAG).d("receiptOcrRequest");
        this.response = response;
        this.settings = receiptOcrSettings;

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
        new FirebaseCloudTextDetection().detectTextRequest(bitmap, this);
    }

    public void deviceImageStorageGetFailure(){
        Timber.tag(TAG).w("deviceImageStorageGetFailure");
        // we weren't able to read this file, something went wrong
        response.cloudDetectReceiptOcrFailure();
        close();
    }

    //// interface for CloudDetectImageTextResponse

    public void cloudDetectImageTextSuccess(TextDetectionResults textDetectionResults){
        Timber.tag(TAG).d("cloudDetectImageTextSuccess");
        //second, do a receipt ocr analysis on the text detection object
        new ReceiptOcrAnalysis().analyzeReceiptRequest(textDetectionResults, settings, this);
    }

    public void cloudDetectImageTextFailure(){
        Timber.tag(TAG).d("cloudDetectImageTextFailure");
        //// didn't get a text detection object
        response.cloudDetectReceiptOcrFailure();
        close();
    }

    /// interface for ReceiptOcrAnalysis.Response
    public void analyzeReceiptComplete(ReceiptOcrResults receiptOcrResults){
        Timber.tag(TAG).d("analyzeReceiptComplete");
        response.cloudDetectReceiptOcrSuccess(receiptOcrResults);
        close();
    }

}
