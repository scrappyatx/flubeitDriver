/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoTake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoRequestUtilities;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 2/16/2018
 * Project : Driver
 */

public class PhotoTakeActivity extends AppCompatActivity implements
        PhotoRequestUtilities.GetPhotoDetailResponse,
        PhotoTakeLayoutComponents.CaptureResponse
{

    private static final String TAG = "PhotoTakeActivity";

    private ActivityNavigator navigator;
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

    }

    @Override
    public void onResume() {
        super.onResume();

        camera = new PhotoTakeLayoutComponents(this);
        camera.onResume();
        camera.setInvisible();

        navigator = new ActivityNavigator();
        controller = new PhotoTakeController();
        controller.getPhotoDetailRequest(this, this);
        Timber.tag(TAG).d("onResume");
    }


    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause");

        camera.onPause();
        controller.close();
        super.onPause();
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("back button pressed");

        if (launchActivityDataFound) {
            Timber.tag(TAG).d("...going to photoList Activity");
            navigator.gotoActivityPhotoDetail(this, batchGuid, stepGuid, photoRequestGuid);
        } else {
            Timber.tag(TAG).d("...going home");
            navigator.gotoActivityHome(this);
        }

    }

    public void clickPhotoButton(View view){
        Timber.tag(TAG).d("clickPhotoButton START...");
        camera.captureRequest(controller.getDevice(), this);
    }

    /// response interface for getPhotoDetailRequest
    public void photoDetailSuccess(PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoDetailSuccess : photoRequestGuid -> " + photoRequest.getGuid());

        launchActivityDataFound = true;
        batchGuid = photoRequest.getBatchGuid();
        stepGuid = photoRequest.getStepGuid();
        photoRequestGuid = photoRequest.getGuid();

        camera.setValues(photoRequest);
        camera.setVisible();

    }

    public void photoDetailFailure(){
        Timber.tag(TAG).d("photoDetailFailure");
        launchActivityDataFound = false;
    }




    public void captureSuccess(PhotoRequest photoRequest){
        Timber.tag(TAG).d("captureSuccess");
        navigator.gotoActivityPhotoDetail(this, photoRequest.getBatchGuid(), photoRequest.getStepGuid(), photoRequest.getGuid());
    }

    public void captureFailure(PhotoRequest photoRequest){
        Timber.tag(TAG).d("captureFailure");
        Timber.tag(TAG).d("batchGuid -> " + photoRequest.getBatchGuid());
        Timber.tag(TAG).d("stepGuid  -> " + photoRequest.getStepGuid());
        Timber.tag(TAG).d("Guid      -> " + photoRequest.getGuid());

        navigator.gotoActivityPhotoDetail(this, photoRequest.getBatchGuid(), photoRequest.getStepGuid(), photoRequest.getGuid());
    }


}
