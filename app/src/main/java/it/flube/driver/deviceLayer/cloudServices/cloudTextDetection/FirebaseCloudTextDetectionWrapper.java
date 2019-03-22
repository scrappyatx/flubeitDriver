/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudTextDetection;

import android.graphics.Bitmap;

import it.flube.driver.deviceLayer.deviceServices.receiptOcr.ReceiptOcrAnalysis;
import it.flube.driver.modelLayer.interfaces.CloudTextDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.TextDetectionResults;
import timber.log.Timber;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class FirebaseCloudTextDetectionWrapper implements
        CloudTextDetectionInterface {
    public static final String TAG = "FirebaseCloudTextDetectionWrapper";

    public void detectImageTextRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, CloudDetectImageTextResponse response){
        Timber.tag(TAG).d("detectImageTextRequest");
        new FirebaseCloudTextStart().textDetectionStart(deviceImageStorage, imageAbsoluteFilename, response);

    }

    public void detectReceiptOcrRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, ReceiptOcrSettings receiptOcrSettings, CloudDetectReceiptOcrResponse response){
        Timber.tag(TAG).d("detectReceiptOcrReqeuest");
        new FirebaseCloudReceiptOcr().receiptOcrRequest(deviceImageStorage, imageAbsoluteFilename, receiptOcrSettings, response);
    }

}
