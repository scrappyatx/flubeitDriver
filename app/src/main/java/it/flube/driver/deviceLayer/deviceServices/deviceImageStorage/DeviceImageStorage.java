/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceImageStorage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import java.io.File;

import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class DeviceImageStorage implements
        DeviceImageStorageInterface {

    private final static String TAG = "DeviceImageStorage";

    public final static String IMAGE_DIR = "imageDir";
    public final static int IMAGE_QUALITY = 25;    /// range 0-100, 0 = MAX COMPRESSION, 100 = NO COMPRESSION

    private Context appContext;

    public DeviceImageStorage(Context appContext){
        this.appContext = appContext;
        Timber.tag(TAG).d("...created");
    }

    public File createUniqueDeviceImageFile(){
        Timber.tag(TAG).d("getImageFilename...");
        File myFile = new File(new ContextWrapper(appContext).getDir(IMAGE_DIR, Context.MODE_PRIVATE), BuilderUtilities.generateGuid());
        Timber.tag(TAG).d("   ...got file name --> " + myFile.getAbsoluteFile().toString());
        return myFile;
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
