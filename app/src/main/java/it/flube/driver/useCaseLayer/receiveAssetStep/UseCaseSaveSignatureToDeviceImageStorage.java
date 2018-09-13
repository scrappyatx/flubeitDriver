/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.receiveAssetStep;

import android.graphics.Bitmap;

import java.util.UUID;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.photoStep.UseCaseSavePhotoToDeviceImageStorage;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.SignatureRequest;
import timber.log.Timber;

/**
 * Created on 8/5/2018
 * Project : Driver
 */
public class UseCaseSaveSignatureToDeviceImageStorage implements
        Runnable,
        DeviceImageStorageInterface.DeleteResponse,
        DeviceImageStorageInterface.SaveResponse,
        CloudActiveBatchInterface.SignatureRequestDeviceAbsoluteFileNameResponse {

    private static final String TAG="UseCaseSaveSignatureToDeviceImageStorage";


    private final MobileDeviceInterface device;
    private final Bitmap bitmap;
    private final Response response;
    private final SignatureRequest signatureRequest;

    private String imageGuid;
    private Boolean saveImageSuccess;

    public UseCaseSaveSignatureToDeviceImageStorage(MobileDeviceInterface device, SignatureRequest signatureRequest, Bitmap bitmap, Response response){
        this.device = device;
        this.bitmap = bitmap;
        this.signatureRequest = signatureRequest;
        this.response = response;
    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        if (signatureRequest.getHasDeviceFile()) {
            /// if we already have an image saved to the device for this photoRequest, we want to delete it
            Timber.tag(TAG).d("...deleting OLD image file -> " + signatureRequest.getDeviceAbsoluteFileName());
            device.getDeviceImageStorage().deleteImageRequest(signatureRequest.getDeviceAbsoluteFileName(), this);
        } else {
            /// go ahead and save the new image

            /// generate a guid for this image
            imageGuid = BuilderUtilities.generateGuid();

            Timber.tag(TAG).d("...no OLD image file to delete, saving new image");
            device.getDeviceImageStorage().saveImageRequest(imageGuid, bitmap, this);
        }
    }

    /// response interface for deleteImageRequest
    public void deviceImageStorageDeleteComplete(){
        //NOW we can save the new image
        Timber.tag(TAG).d("...deviceImageStorageDeleteComplete, saving new image");
        device.getDeviceImageStorage().saveImageRequest(imageGuid, bitmap, this);
    }

    /// response interface for saveImageRequest to device storage
    public void deviceImageStorageSaveSuccess(String absoluteFileName) {
        Timber.tag(TAG).d("deviceImageStorageSaveSuccess : absoluteFileName = " + absoluteFileName);
        saveImageSuccess = true;

        ///update our photoRequest object in the cloud to reflect we have saved an image on the device for this photo request

        device.getCloudActiveBatch().updateSignatureRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), signatureRequest, absoluteFileName, true, this);
    }

    public void deviceImageStorageSaveFailure() {
        Timber.tag(TAG).d("deviceImageStorageSaveFailure");
        saveImageSuccess = false;

        ///update our photoRequest object in the cloud to reflect we DON'T have an image save on this device for this photo request
        device.getCloudActiveBatch().updateSignatureRequestDeviceAbsoluteFileNameRequest(device.getCloudAuth().getDriver(), signatureRequest, null, false, this);

    }

    /// response interface for updating photoRequest with file name for device image
    public void cloudActiveBatchUpdateSignatureRequestDeviceAbsoluteFilenameComplete(){
        Timber.tag(TAG).d("cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete");

        if (saveImageSuccess){
            response.signatureSavedSuccess(imageGuid);
        } else {
            response.signatureSavedFailure(imageGuid);
        }
    }

    public interface Response {
        void signatureSavedSuccess(String imageGuid);

        void signatureSavedFailure(String imageGuid);
    }

}
