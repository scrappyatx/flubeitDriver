/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoTake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoRequestUtilities;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.libbatchdata.utilities.BuilderUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class PhotoTakeActivity extends AppCompatActivity implements
        PhotoRequestUtilities.GetPhotoDetailResponse,
        PhotoTakeLayoutComponents.CaptureResponse {

    private static final String TAG = "PhotoTakeActivity";

    private String activityGuid;

    private PhotoTakeController controller;
    private PhotoTakeLayoutComponents camera;

    private String batchGuid;
    private String stepGuid;
    private String photoRequestGuid;
    private Boolean launchActivityDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_take);

        controller = new PhotoTakeController();
        camera = new PhotoTakeLayoutComponents(this);


        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        controller.getPhotoDetailRequest(this, this);
        camera.onResume();
        super.onResume();
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }


    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        camera.onPause();
        super.onPause();
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);

        if (launchActivityDataFound) {
            Timber.tag(TAG).d("...going to photoList Activity");
            ActivityNavigator.getInstance().gotoActivityPhotoDetail(this, batchGuid, stepGuid, photoRequestGuid);
        } else {
            Timber.tag(TAG).d("...going home");
            ActivityNavigator.getInstance().gotoActivityHome(this);
        }

    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
        controller.close();
        camera.close();
        controller = null;
        camera = null;
        super.onDestroy();

    }

    public void clickPhotoButton(View view){
        Timber.tag(TAG).d("clickPhotoButton (%s) START...", activityGuid);
        camera.captureRequest(AndroidDevice.getInstance(), this);
    }

    /// response interface for getPhotoDetailRequest
    public void photoDetailSuccess(Driver driver, PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoDetailSuccess (%s) : photoRequestGuid -> " + photoRequest.getGuid(), activityGuid);

        launchActivityDataFound = true;
        batchGuid = photoRequest.getBatchGuid();
        stepGuid = photoRequest.getStepGuid();
        photoRequestGuid = photoRequest.getGuid();

        camera.setValues(photoRequest);
        camera.setVisible();
    }

    public void photoDetailFailureNoDriver(){
        Timber.tag(TAG).d("photoDetailFailureNoDriver (%s)", activityGuid);
        launchActivityDataFound = false;
    }

    public void photoDetailFailureDriverButNoPhotoRequest(Driver driver){
        Timber.tag(TAG).d("photoDetailFailureDriverButNoPhotoRequest (%s)", activityGuid);
        launchActivityDataFound = false;
    }

    public void photoDetailFailureIntentKeysNotFound(){
        Timber.tag(TAG).d("photoDetailFailureIntentKeysNotFound (%s)", activityGuid);
        launchActivityDataFound = false;
    }

    //// interface for PhotoTakeLayoutComponents.CaptureResponse


    public void captureSuccess(PhotoRequest photoRequest){
        Timber.tag(TAG).d("captureSuccess (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityPhotoDetail(this, photoRequest.getBatchGuid(), photoRequest.getStepGuid(), photoRequest.getGuid());
    }

    public void captureFailure(PhotoRequest photoRequest){
        Timber.tag(TAG).d("captureFailure (%s)", activityGuid);
        Timber.tag(TAG).d("batchGuid -> " + photoRequest.getBatchGuid());
        Timber.tag(TAG).d("stepGuid  -> " + photoRequest.getStepGuid());
        Timber.tag(TAG).d("Guid      -> " + photoRequest.getGuid());

        ActivityNavigator.getInstance().gotoActivityPhotoDetail(this, photoRequest.getBatchGuid(), photoRequest.getStepGuid(), photoRequest.getGuid());
    }


}
