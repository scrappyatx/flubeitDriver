/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public interface DeviceImageStorageInterface {

    File createUniqueDeviceImageFile();

    void saveImageRequest(String imageGuid, Bitmap bitmap, SaveResponse response);

    interface SaveResponse {
        void deviceImageStorageSaveSuccess(String absoluteFileName);

        void deviceImageStorageSaveFailure();
    }

    void deleteImageRequest(String absoluteFileName, DeleteResponse response);

    interface DeleteResponse {
        void deviceImageStorageDeleteComplete();
    }

    void getImageRequest(String absoluteFileName, GetResponse response);

    interface GetResponse {
        void deviceImageStorageGetSuccess(Bitmap bitmap);

        void deviceImageStorageGetFailure();
    }

}
