/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep;

import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.activeBatch.UseCaseGetActiveBatchPhotoRequest;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoStepConstants.BATCH_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoStepConstants.ORDER_STEP_GUID_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoStepConstants.PHOTO_REQUEST_GUID_KEY;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class PhotoRequestUtilities implements
    UseCaseGetActiveBatchPhotoRequest.Response {
    private static final String TAG = "PhotoRequestUtilities";

    private GetPhotoDetailResponse response;

    public void getPhotoRequest(AppCompatActivity activity, MobileDeviceInterface device, GetPhotoDetailResponse response) {
        Timber.tag(TAG).d("getActivityLaunchDataRequest START...");
        this.response = response;

        if (activity.getIntent().hasExtra(BATCH_GUID_KEY)) {
            if (activity.getIntent().hasExtra(ORDER_STEP_GUID_KEY)) {
                if (activity.getIntent().hasExtra(PHOTO_REQUEST_GUID_KEY)) {
                    //get photoRequestData
                    String batchGuid = activity.getIntent().getStringExtra(BATCH_GUID_KEY);
                    String stepGuid = activity.getIntent().getStringExtra(ORDER_STEP_GUID_KEY);
                    String photoRequestGuid = activity.getIntent().getStringExtra(PHOTO_REQUEST_GUID_KEY);

                    Timber.tag(TAG).d("   ...batchGuid -> " + batchGuid);
                    Timber.tag(TAG).d("   ...stepGuid  -> " + stepGuid);
                    Timber.tag(TAG).d("   ...photoRequestGuid  -> " + photoRequestGuid);

                    //then use the activity launch data to get the actual photoDetail object
                    device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseGetActiveBatchPhotoRequest(device, batchGuid, stepGuid, photoRequestGuid, this));

                } else {
                    Timber.tag(TAG).d("   ...batchGuidKey NOT FOUND");
                    response.photoDetailFailure();
                }
            } else {
                Timber.tag(TAG).d("   ...orderStepGuidKey NOT FOUND");
                response.photoDetailFailure();
            }
        } else {
            Timber.tag(TAG).d("   ...photoRequestGuidKey NOT FOUND");
            response.photoDetailFailure();
        }
    }

    /// response interface for UseCaseGetActiveBatchPhotoRequest.Response
    public void useCaseGetActiveBatchPhotoRequestSuccess(PhotoRequest photoRequest){
        response.photoDetailSuccess(photoRequest);
    }

    public void useCaseGetActiveBatchPhotoRequestFailure(){
        response.photoDetailFailure();
    }


    public interface GetPhotoDetailResponse {
        void photoDetailSuccess(PhotoRequest photoRequest);

        void photoDetailFailure();
    }

}
