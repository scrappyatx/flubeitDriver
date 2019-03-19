/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

import java.util.HashMap;

import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.TextDetectionResults;

/**
 * Created on 2/28/2019
 * Project : Driver
 */
public interface CloudTextDetectionInterface {

    void detectImageTextRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, CloudDetectImageTextResponse response);

    interface CloudDetectImageTextResponse {
        void cloudDetectImageTextSuccess(TextDetectionResults textDetectionResults);

        void cloudDetectImageTextFailure();
    }

    void detectReceiptOcrRequest(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, ReceiptOcrSettings receiptOcrSettings, CloudDetectReceiptOcrResponse response);


    interface CloudDetectReceiptOcrResponse {
        void cloudDetectReceiptOcrSuccess(ReceiptOcrResults receiptOcrResults);

        void cloudDetectReceiptOcrFailure();
    }
}

