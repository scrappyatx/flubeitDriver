/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoTake;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.useCaseLayer.authorizePaymentStep.UseCaseReceiptImageDeviceAbsoluteFilename;
import it.flube.driver.useCaseLayer.photoStep.UseCasePhotoImageDeviceAbsoluteFilename;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoRequestUtilities;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.ReceiptRequest;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class PhotoTakeController implements
    UseCasePhotoImageDeviceAbsoluteFilename.Response {

    private static final String TAG = "PhotoTakeController";

    private UpdatePhotoRequestWithImageDeviceFilenameResponse updateResponse;

    public PhotoTakeController() {
        Timber.tag(TAG).d("controller CREATED");
    }

    /// Get the photoRequest for the activity to display
    public void getPhotoDetailRequest(AppCompatActivity activity, PhotoRequestUtilities.GetPhotoDetailResponse response){
        Timber.tag(TAG).d("getPhotoDetailRequest");
        //first get the data that was used to launch the activity
        new PhotoRequestUtilities().getPhotoRequest(activity, AndroidDevice.getInstance(), response);
    }

    //update the photoRequest with the imageDeviceAbsoluteFilename
    public void updatePhotoRequestWithImageDeviceFilename(String imageDeviceAbsoluteFileName, PhotoRequest photoRequest, UpdatePhotoRequestWithImageDeviceFilenameResponse updateResponse){
        Timber.tag(TAG).d("updatePhotoRequestWithImageDeviceFilename");
        this.updateResponse = updateResponse;
        AndroidDevice.getInstance().getUseCaseEngine().getUseCaseExecutor().execute(new UseCasePhotoImageDeviceAbsoluteFilename(AndroidDevice.getInstance(),imageDeviceAbsoluteFileName, photoRequest, this));
    }

    public void close(){
        Timber.tag(TAG).d("close");
        updateResponse = null;
    }

    /// UseCase interface response
    public void useCasePhotoRequestImageDeviceAbsoluteFilenameComplete(PhotoRequest photoRequest){
        Timber.tag(TAG).d("useCasePhotoRequestImageDeviceAbsoluteFilenameComplete");
        updateResponse.updatePhotoRequestWithImageDeviceFilenameComplete(photoRequest);
    }

    public interface UpdatePhotoRequestWithImageDeviceFilenameResponse {
        void updatePhotoRequestWithImageDeviceFilenameComplete(PhotoRequest photoRequest);
    }


}
