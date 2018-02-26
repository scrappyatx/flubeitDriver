/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep;

import android.graphics.Bitmap;

import com.wonderkiln.camerakit.CameraKitEventCallback;
import com.wonderkiln.camerakit.CameraKitImage;

import java.util.concurrent.ExecutorService;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.photoStep.UseCaseSavePhotoToDeviceImageStorage;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class PhotoTakeController implements
        CameraKitEventCallback<CameraKitImage>,
        UseCaseSavePhotoToDeviceImageStorage.Response {

    private static final String TAG = "PhotoTakeController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;
    private Response response;


    public PhotoTakeController(Response response) {
        Timber.tag(TAG).d("controller CREATED");
        device = AndroidDevice.getInstance();
        useCaseExecutor = device.getUseCaseEngine().getUseCaseExecutor();
        this.response = response;
    }

    public void close(){
        useCaseExecutor = null;
        device = null;
    }

    /// cameraKitEventCallback interface
    public void callback(CameraKitImage event){
        //byte[] jpeg = event.getJpeg();

        //get the bitmap for the image
        Bitmap bitmap = event.getBitmap();

        //generate a guid for this image
        String imageGuid = BuilderUtilities.generateGuid();

        //save this image in local device storage
        useCaseExecutor.execute(new UseCaseSavePhotoToDeviceImageStorage(device, imageGuid, event.getBitmap(), this));

        Timber.tag(TAG).d("got image");
        response.photoComplete(imageGuid);
    }

    public void imageSavedSuccess(String imageGuid){
        Timber.tag(TAG).d("saved image SUCCESS, image guid -> " + imageGuid);
    }

    public void imageSavedFailure(String imageGuid){
        Timber.tag(TAG).d("save image FAILURE, image guid -> " + imageGuid);
    }

    //// cameraKitEvent interace
    public interface Response {
        void photoComplete(String imageGuid);
    }

}
