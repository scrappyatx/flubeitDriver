/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceTextDetection;

import android.graphics.Bitmap;

import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceTextDetectionInterface;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import timber.log.Timber;

/**
 * Created on 3/1/2019
 * Project : Driver
 */
public class FirebaseDeviceTextDetectionWrapper implements DeviceTextDetectionInterface {
    public static final String TAG = "FirebaseCloudTextDetectionWrapper";

    public void detectImageTextRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, DeviceDetectImageTextResponse response){
        Timber.tag(TAG).d("detectImageTextRequest");
        new FirebaseDeviceTextStart().textDetectionStart(deviceImageStorage, imageAbsoluteFilename, response);
    }

    public  void detectReceiptOcrRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, ReceiptOcrSettings receiptOcrSettings, DeviceDetectReceiptOcrResponse response){
        Timber.tag(TAG).d("detectReceiptOcrReqeuest");
        new FirebaseDeviceReceiptOcr().receiptOcrRequest(deviceImageStorage, imageAbsoluteFilename, receiptOcrSettings, response);
    }
}
