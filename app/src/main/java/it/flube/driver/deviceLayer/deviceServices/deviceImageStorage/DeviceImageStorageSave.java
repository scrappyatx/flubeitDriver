/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.deviceImageStorage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.deviceServices.deviceImageStorage.DeviceImageStorage.IMAGE_DIR;
import static it.flube.driver.deviceLayer.deviceServices.deviceImageStorage.DeviceImageStorage.IMAGE_QUALITY;

/**
 * Created on 2/21/2018
 * Project : Driver
 */

public class DeviceImageStorageSave {
    public static final String TAG = "DeviceImageStorageSave";

    public void saveImageRequest(Context appContext, String imageGuid, Bitmap bitmap, DeviceImageStorageInterface.SaveResponse response) {

        Timber.tag(TAG).d("saveImageRequest START...");

        ContextWrapper cw = new ContextWrapper(appContext);

        //save the file
        try {
            Timber.tag(TAG).d("   ...bitmap image info");
            Timber.tag(TAG).d("          height        -> " + bitmap.getHeight());
            Timber.tag(TAG).d("          width         -> " + bitmap.getWidth());
            Timber.tag(TAG).d("          size (bytes) - > " + bitmap.getByteCount());

            //file name is the imageGuid
            File myFile = new File(cw.getDir(IMAGE_DIR, Context.MODE_PRIVATE), imageGuid);
            Timber.tag(TAG).d("   ...got file name --> " + myFile.getAbsoluteFile().toString());

            FileOutputStream stream = new FileOutputStream(myFile);
            Timber.tag(TAG).d("   ...got output file stream");

            Timber.tag(TAG).d("   ...compressing bitmap");
            bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, stream);

            stream.flush();
            Timber.tag(TAG).d("   ...flushing stream");

            stream.close();
            Timber.tag(TAG).d("   ...file saved SUCCESS!");

            Long fileSize = myFile.length();

            Timber.tag(TAG).d("   ...file size bytes : " + fileSize.toString());

            response.deviceImageStorageSaveSuccess(myFile.getAbsoluteFile().toString());

        } catch (Exception e) {
            Timber.tag(TAG).d("   ...file save FAILURE!!!!");
            Timber.tag(TAG).e(e);
            response.deviceImageStorageSaveFailure();
        }
        Timber.tag(TAG).d("...saveImageRequest COMPLETE");
    }
}
