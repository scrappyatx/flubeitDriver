/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoDetail;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoRequestUtilities;
import timber.log.Timber;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class PhotoDetailController {
    private final String TAG = "PhotoController";

    private MobileDeviceInterface device;

    public PhotoDetailController() {
        Timber.tag(TAG).d("controller CREATED");
        device = AndroidDevice.getInstance();
    }

    /// Get the photoRequest for the activity to display
    public void getPhotoDetailRequest(AppCompatActivity activity, PhotoRequestUtilities.GetPhotoDetailResponse response){
        //first get the data that was used to launch the activity
        new PhotoRequestUtilities().getPhotoRequest(activity, device, response);
    }


    public void close() {
        device = null;
    }

}

