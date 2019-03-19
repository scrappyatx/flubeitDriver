/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

import it.flube.libbatchdata.entities.ReceiptOcrResults;
import it.flube.libbatchdata.entities.ReceiptOcrSettings;
import it.flube.libbatchdata.entities.VehicleDetectionResults;

/**
 * Created on 2/28/2019
 * Project : Driver
 */
public interface CloudVehicleDetectionInterface {
    void detectVehicleRequest(Bitmap bitmap, CloudDetectVehicleResponse response);


    interface CloudDetectVehicleResponse {
        void cloudDetectVehicleSuccess(VehicleDetectionResults vehicleDetectionResults);

        void cloudDetectVehicleFailure();
    }
}
