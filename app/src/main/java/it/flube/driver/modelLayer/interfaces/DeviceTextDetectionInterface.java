/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.TextDetectionResults;

/**
 * Created on 2/28/2019
 * Project : Driver
 */
public interface DeviceTextDetectionInterface {
    void detectImageTextRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, DeviceDetectImageTextResponse response);

    interface DeviceDetectImageTextResponse {
        void deviceDetectImageTextSuccess(TextDetectionResults textDetectionResults);

        void deviceDetectImageTextFailure();
    }

    void detectReceiptOcrRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, ReceiptOcrSettings receiptOcrSettings, DeviceDetectReceiptOcrResponse response);


    interface DeviceDetectReceiptOcrResponse {
        void deviceDetectReceiptOcrSuccess(ReceiptOcrResults receiptOcrResults);

        void deviceDetectReceiptOcrFailure();
    }
}
