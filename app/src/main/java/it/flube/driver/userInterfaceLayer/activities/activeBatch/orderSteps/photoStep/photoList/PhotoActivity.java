/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchUtilities;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.entities.PhotoRequest;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderAuthorizePaymentStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 10/26/2017
 * Project : Driver
 */

public class PhotoActivity extends AppCompatActivity implements
        PhotoController.GetDriverAndActiveBatchStepResponse,
        PhotoActivityLayoutComponents.Response,
        PhotoController.StepFinishedResponse {

    private static final String TAG = "PhotoActivity";


    private PhotoController controller;
    private PhotoActivityLayoutComponents layoutComponents;

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityGuid= BuilderUtilities.generateGuid();

        setContentView(R.layout.activity_photo_step);


        controller = new PhotoController();
        layoutComponents = new PhotoActivityLayoutComponents(this, this);

        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onStart(){
        Timber.tag(TAG).d("onStart (%s)", activityGuid);
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        DrawerMenu.getInstance().setActivityDontMonitorActiveBatch(this, R.string.photo_step_activity_title);
        controller.getDriverAndActiveBatchStep(this);
        Timber.tag(TAG).d("onResume (%s)", activityGuid);
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause (%s)", activityGuid);
        DrawerMenu.getInstance().close();
        super.onPause();
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
        layoutComponents.close();
        super.onDestroy();
    }


    /// PhotoActivityLayoutComponents interface
    public void stepCompleteButtonClicked(ArrayList<PhotoRequest> photoList, String milestoneWhenFinished){
        Timber.tag(TAG).d("clicked step complete button (%s)", activityGuid);
        layoutComponents.showWaitingAnimationAndBanner(getString(R.string.photo_step_completed_banner_text));
        controller.stepFinishedRequest(photoList, milestoneWhenFinished, this);
    }

    //// photo list interface
    public void photoRequestSelected(PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoRequestSelected (%s) -> " + photoRequest.getGuid(), activityGuid);
        ActivityNavigator.getInstance().gotoActivityPhotoDetail(this, photoRequest.getBatchGuid(), photoRequest.getStepGuid(), photoRequest.getGuid());
    }

    ///
    ///  PhotoController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderPhotoStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep (%s)", activityGuid);
        layoutComponents.setValues(this,orderStep);
        layoutComponents.setVisible();
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityLogin(this);
    }

    public void gotDriverButNoStep(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoStep (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
    }

    public void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType){
        Timber.tag(TAG).d("gotStepMismatch (%s), taskType -> " + taskType.toString(), activityGuid);
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

    ////
    //// StepFinsished interface
    ////
    public void stepFinished(){
        Timber.tag(TAG).d("stepFinished");
        //go to the next step
        ActivityNavigator.getInstance().gotoActiveBatchStep(this);
    }

}
