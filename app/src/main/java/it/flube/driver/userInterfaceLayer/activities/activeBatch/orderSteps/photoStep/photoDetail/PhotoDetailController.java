/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoDetail;

import android.support.v7.app.AppCompatActivity;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.account.UseCaseGetDriver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoRequestUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class PhotoDetailController implements
        PhotoRequestUtilities.GetPhotoDetailResponse {

    private final String TAG = "PhotoDetailController";

    private GetDriverAndPhotoDetailResponse response;


    public PhotoDetailController() {
        Timber.tag(TAG).d("created");
    }

    public void getDriverAndPhotoDetailRequest(AppCompatActivity activity, GetDriverAndPhotoDetailResponse response){
        Timber.tag(TAG).d("getDriverAndPhotoDetailRequest");
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        this.response = response;

        //first get the data that was used to launch the activity
        new PhotoRequestUtilities().getPhotoRequest(activity, AndroidDevice.getInstance(), this);

    }



    /// response interface for PhotoRequestUtilities.getPhotoRequest

    public void photoDetailSuccess(Driver driver, PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoDetailSuccess : photoRequestGuid -> " + photoRequest.getGuid());
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        response.gotDriverAndPhotoRequest(driver, photoRequest);
    }

    public void photoDetailFailureNoDriver(){
        Timber.tag(TAG).d("photoDetailFailureNoDriver");
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        response.gotNoDriver();
    }

    public void photoDetailFailureDriverButNoPhotoRequest(Driver driver){
        Timber.tag(TAG).d("photoDetailFailureDriverButNoPhotoRequest");
        response.gotDriverButNoPhotoRequest(driver);
    }

    public void photoDetailFailureIntentKeysNotFound(){
        Timber.tag(TAG).d("photoDetailFailureIntentKeysNotFound");
        response.intentKeysNotFound();
    }

    //// clean up when we are done
    public void close() {
        Timber.tag(TAG).d("close");
        response = null;
    }


    public interface GetDriverAndPhotoDetailResponse {
        void gotDriverAndPhotoRequest(Driver driver, PhotoRequest photoRequest);

        void gotDriverButNoPhotoRequest(Driver driver);

        void gotNoDriver();

        void intentKeysNotFound();
    }

}

