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
public class PhotoDetailController
        implements
            UseCaseGetDriver.Response,
            PhotoRequestUtilities.GetPhotoDetailResponse {
    private final String TAG = "PhotoDetailController";

    private GetDriverAndPhotoDetailResponse response;
    private PhotoRequest photoRequest;
    private Boolean gotPhotoRequest;

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

    /// Get the photoRequest for the activity to display
    //public void getPhotoDetailRequest(AppCompatActivity activity, PhotoRequestUtilities.GetPhotoDetailResponse response){
    //    Timber.tag(TAG).d("getPhotoDetailRequest");
    //    //first get the data that was used to launch the activity
    //    new PhotoRequestUtilities().getPhotoRequest(activity, AndroidDevice.getInstance(), response);
    //}


    public void close() {
        Timber.tag(TAG).d("close");
    }

    /// response interface for PhotoRequestUtilities.getPhotoRequest

    public void photoDetailSuccess(PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoDetailSuccess : photoRequestGuid -> " + photoRequest.getGuid());
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        this.photoRequest = photoRequest;
        gotPhotoRequest = true;
        //now get the driver
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriver(AndroidDevice.getInstance(), this));
    }

    public void photoDetailFailure(){
        Timber.tag(TAG).d("photoDetailFailure");
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        gotPhotoRequest = false;
        //now get the driver
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetDriver(AndroidDevice.getInstance(), this));
    }

    /// response interface for UseCaseGetDriver
    public void useCaseGetDriverSuccess(Driver driver){
        Timber.tag(TAG).d("useCaseGetDriverSuccess");
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (gotPhotoRequest){
            Timber.tag(TAG).d("   ...do have photoRequest");
            response.gotDriverAndPhotoRequest(driver, photoRequest);
        } else {
            Timber.tag(TAG).d("   ...don't have photoRequest");
            response.gotDriverButNoPhotoRequest(driver);
        }

    }

    public void useCaseGetDriverFailure(){
        Timber.tag(TAG).d("useCaseGetDriverFailure");
        response.gotNoDriver();
    }


    public interface GetDriverAndPhotoDetailResponse {
        void gotDriverAndPhotoRequest(Driver driver, PhotoRequest photoRequest);

        void gotDriverButNoPhotoRequest(Driver driver);

        void gotNoDriver();
    }

}

