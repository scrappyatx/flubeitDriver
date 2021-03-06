/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoTake;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoRequestUtilities;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class PhotoTakeController {

    private static final String TAG = "PhotoTakeController";


    public PhotoTakeController() {
        Timber.tag(TAG).d("controller CREATED");
    }

    /// Get the photoRequest for the activity to display
    public void getPhotoDetailRequest(AppCompatActivity activity, PhotoRequestUtilities.GetPhotoDetailResponse response){
        Timber.tag(TAG).d("getPhotoDetailRequest");
        //first get the data that was used to launch the activity
        new PhotoRequestUtilities().getPhotoRequest(activity, AndroidDevice.getInstance(), response);
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }




}
