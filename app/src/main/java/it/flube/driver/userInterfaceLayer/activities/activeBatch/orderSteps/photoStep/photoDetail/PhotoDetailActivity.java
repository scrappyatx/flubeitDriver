/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoRequestUtilities;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 1/22/2018
 * Project : Driver
 */

public class PhotoDetailActivity extends AppCompatActivity implements
    PhotoRequestUtilities.GetPhotoDetailResponse {

    private static final String TAG = "PhotoDetailActivity";

    private ActivityNavigator navigator;
    private PhotoDetailController controller;
    private DrawerMenu drawer;

    private PhotoRequestDetailLayoutComponents requestDetail;
    private StepDetailCompleteButtonComponents stepComplete;

    private String batchGuid;
    private String serviceOrderGuid;
    private String stepGuid;
    private Boolean launchActivityDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_detail);
        requestDetail = new PhotoRequestDetailLayoutComponents(this);
        stepComplete = new StepDetailCompleteButtonComponents(this, "Take a Photo");

        requestDetail.setInvisible();
        stepComplete.setInvisible();
    }

    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.photo_detail_activity_title);
        controller = new PhotoDetailController();

        ///get the photoDetail that was used to launch this activity
        controller.getPhotoDetailRequest(this, this);



        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause");
        super.onPause();
        controller.close();

    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("back button pressed");
        navigator.gotoActiveBatchStep(this);

        if (launchActivityDataFound) {
            Timber.tag(TAG).d("...going to photoList Activity");
            navigator.gotoActivityPhotoList(this, batchGuid, serviceOrderGuid, stepGuid, OrderStepInterface.TaskType.TAKE_PHOTOS);
        } else {
            Timber.tag(TAG).d("...going home");
            navigator.gotoActivityHome(this);
        }
    }

    public void clickStepCompleteButton(View v){
        Timber.tag(TAG).d("take a photo button clicked");
        requestDetail.setGone();
        stepComplete.showWaitingAnimatingWithNoBanner();
        navigator.gotoActivityPhotoTake(this,  requestDetail.getPhotoRequest().getBatchGuid(), requestDetail.getPhotoRequest().getStepGuid(), requestDetail.getPhotoRequest().getGuid());
    }


    /// response interface for getPhotoDetailRequest
    public void photoDetailSuccess(PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoDetailSuccess : photoRequestGuid -> " + photoRequest.getGuid());

        launchActivityDataFound = true;
        batchGuid = photoRequest.getBatchGuid();
        serviceOrderGuid = photoRequest.getServiceOrderGuid();
        stepGuid = photoRequest.getStepGuid();

        requestDetail.setValues(this, photoRequest);
        requestDetail.setVisible();
        stepComplete.setVisible();
    }

    public void photoDetailFailure(){
        Timber.tag(TAG).d("photoDetailFailure");
        launchActivityDataFound = false;
    }

}
