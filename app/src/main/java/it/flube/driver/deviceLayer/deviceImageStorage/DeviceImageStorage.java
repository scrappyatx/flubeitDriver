/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceImageStorage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class DeviceImageStorage implements
        DeviceImageStorageInterface {

    private final static String TAG = "DeviceImageStorage";

    public final static String IMAGE_DIR = "imageDir";
    public final static int IMAGE_QUALITY = 100;

    private Context appContext;

    public DeviceImageStorage(Context appContext){
        this.appContext = appContext;
        Timber.tag(TAG).d("...created");
    }

    public void saveImageRequest(String imageGuid, Bitmap bitmap, DeviceImageStorageInterface.SaveResponse response) {
        Timber.tag(TAG).d("saveImageRequest...");
        new DeviceImageStorageSave().saveImageRequest(appContext, imageGuid, bitmap, response);
    }

    public void deleteImageRequest(String absoluteFileName, DeviceImageStorageInterface.DeleteResponse response) {
        Timber.tag(TAG).d("deleteImageRequest...");
        new DeviceImageStorageDelete().deleteImageRequest(absoluteFileName, response);
    }

    public void getImageRequest(String absoluteFileName, DeviceImageStorageInterface.GetResponse response) {
        Timber.tag(TAG).d("getImageRequest...");
        new DeviceImageStorageGet().getImageRequest(absoluteFileName, response);
    }

}
