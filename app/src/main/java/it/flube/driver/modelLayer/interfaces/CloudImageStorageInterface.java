/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public interface CloudImageStorageInterface {

    void addDeviceImageToActiveBatchUploadList(Driver driver, DeviceInfo deviceInfo, String deviceStorgageAbsoluteFileName, String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid, AddDeviceImageResponse response);

    interface AddDeviceImageResponse {
        void cloudImageStorageAddDeviceImageComplete();
    }

    void startOrResumeUploadingImagesForActiveBatch(Driver driver, DeviceInfo deviceInfo, String batchGuid, StartOrResumeResponse response);

    interface StartOrResumeResponse {
        void cloudImageStorageStartOrResumeComplete();
    }

    void waitForAllImageFilesForActiveBatchToFinishUploading(Driver driver, DeviceInfo deviceInfo, String batchGuid, WaitForUploadToFinishResponse response);

    interface WaitForUploadToFinishResponse {
        void cloudImageStorageAllUploadsComplete();
    }

}
