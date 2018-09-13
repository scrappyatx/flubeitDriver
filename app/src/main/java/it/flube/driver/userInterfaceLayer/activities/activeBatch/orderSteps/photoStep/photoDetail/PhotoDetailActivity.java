/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import it.flube.driver.R;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoRequestUtilities;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 1/22/2018
 * Project : Driver
 */

public class PhotoDetailActivity extends AppCompatActivity implements
        PhotoDetailLayoutComponents.Response,
        PhotoDetailController.GetDriverAndPhotoDetailResponse {

    private static final String TAG = "PhotoDetailActivity";

    private String activityGuid;

    private PhotoDetailController controller;
    private PhotoDetailLayoutComponents layoutComponents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_detail);

        DrawerMenu.getInstance().setActivity(this, R.string.photo_detail_activity_title);
        controller = new PhotoDetailController();
        layoutComponents = new PhotoDetailLayoutComponents(this, this);

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
    }

    @Override
    public void onResume() {
        super.onResume();

        DrawerMenu.getInstance().setActivity(this, R.string.photo_detail_activity_title);
        ///get the photoDetail that was used to launch this activity
        controller.getDriverAndPhotoDetailRequest(this, this);
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        DrawerMenu.getInstance().clearActivity();
        super.onPause();
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop (%s)", activityGuid);
        super.onStop();
    }

    @Override
    public void onDestroy(){
        Timber.tag(TAG).d("onDestroy (%s)", activityGuid);
        DrawerMenu.getInstance().close();
        controller.close();
        layoutComponents.close();
        super.onDestroy();

    }

    /// photo
    public void takePhotoButtonClicked(String batchGuid, String stepGuid, String photoRequestGuid){
        Timber.tag(TAG).d("take a photo button clicked (%s)", activityGuid);
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        layoutComponents.showWaitingAnimation();
        ActivityNavigator.getInstance().gotoActivityPhotoTake(this,  batchGuid, stepGuid, photoRequestGuid);
    }


    ///response interface for PhotoDetailController.GetDriverAndPhotoRequest
    public void gotDriverAndPhotoRequest(Driver driver, PhotoRequest photoRequest){
        Timber.tag(TAG).d("gotDriverAndPhotoRequest (%s)", activityGuid);
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());

        layoutComponents.setValues(this, photoRequest);
        layoutComponents.setVisible();
    }

    public void gotDriverButNoPhotoRequest(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoPhotoRequest (%s)", activityGuid);
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver (%s)", activityGuid);
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        ActivityNavigator.getInstance().gotoActivityLogin(this);
    }

}
