/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceImageStorage;

import java.io.File;

import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 2/21/2018
 * Project : Driver
 */

public class DeviceImageStorageDelete {
    public static final String TAG = "DeviceImageStorageDelete";


    public void deleteImageRequest(String absoluteFileName, DeviceImageStorageInterface.DeleteResponse response) {
        Timber.tag(TAG).d("deleteImageRequest START...");

        try {
            File myFile = new File(absoluteFileName);
            Timber.tag(TAG).d("   ...got file name --> " + myFile.getAbsoluteFile());

            if (myFile.delete()){
                Timber.tag(TAG).d("   ...file delete result -> true");
            } else {
                Timber.tag(TAG).d("   ...file delete result -> false");
            }

        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            Timber.tag(TAG).d("   ...delete file FAILURE!!!!");
        }
        response.deviceImageStorageDeleteComplete();
        Timber.tag(TAG).d("...deleteImageRequest COMPLETE");
    }
}
