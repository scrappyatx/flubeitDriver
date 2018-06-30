/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceImageStorage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 2/21/2018
 * Project : Driver
 */

public class DeviceImageStorageGet {
    public static final String TAG = "DeviceImageStorageGet";

    public DeviceImageStorageGet(){
        Timber.tag(TAG).d("created...");
    }

    public void getImageRequest(String absoluteFileName, DeviceImageStorageInterface.GetResponse response) {
        Timber.tag(TAG).d("getImageRequest START...");

        try {
            //file name is the imageGuid
            File myFile = new File(absoluteFileName);
            Timber.tag(TAG).d("   ...got file name --> " + myFile.getAbsoluteFile());

            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(myFile));
            Timber.tag(TAG).d("   ...got bitmap SUCCESS!");
            Timber.tag(TAG).d("          height        -> " + bitmap.getHeight());
            Timber.tag(TAG).d("          width         -> " + bitmap.getWidth());
            Timber.tag(TAG).d("          size (bytes) - > " + bitmap.getByteCount());

            response.deviceImageStorageGetSuccess(bitmap);

        } catch (FileNotFoundException e) {
            Timber.tag(TAG).d("   ...file not found FAILURE!!!");
            response.deviceImageStorageGetFailure();

        } catch (Exception e){
            Timber.tag(TAG).d("   ...got bitmap FAILURE!!!");
            response.deviceImageStorageGetFailure();

        }
        Timber.tag(TAG).d("...getImageRequest COMPLETE");
    }

}
