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

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchUtilities;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.libbatchdata.entities.PhotoRequest;
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
        ActiveBatchUtilities.GetStepResponse,
        SlideView.OnSlideCompleteListener,
        ActiveBatchAlerts.ServiceOrderCompletedAlertHidden,
        PhotoRequestListAdapter.Response {

    private static final String TAG = "PhotoActivity";

    private ActivityNavigator navigator;
    private PhotoController controller;
    private DrawerMenu drawer;

    private StepDetailTitleLayoutComponents stepTitle;
    private StepDetailDueByLayoutComponents stepDueBy;
    private PhotoRequestListLayoutComponents photoList;
    private StepDetailSwipeCompleteButtonComponent stepComplete;

    private String milestoneEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_step);

        stepTitle = new StepDetailTitleLayoutComponents(this);
        stepDueBy = new StepDetailDueByLayoutComponents(this);
        photoList = new PhotoRequestListLayoutComponents(this, this);
        stepComplete = new StepDetailSwipeCompleteButtonComponent(this, getResources().getString(R.string.photo_step_completed_step_button_caption), this);

    }

    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.photo_step_activity_title);
        controller = new PhotoController();
        controller.getActivityLaunchInfo(this,this);

        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        Timber.tag(TAG).d("onPause");
        super.onPause();

        drawer.close();
        controller.close();

        EventBus.getDefault().unregister(this);
    }


    public void onSlideComplete(SlideView v){
        Timber.tag(TAG).d("clicked step complete button");

        photoList.setGone();
        stepComplete.showWaitingAnimationAndBanner(getString(R.string.photo_step_completed_banner_text));
        controller.stepFinished(photoList.getPhotoRequestList(), milestoneEvent);
    }

    ///
    /// Activity Launch Info - orderStep response
    ///
    public void getStepSuccess(OrderStepInterface orderStep){
        Timber.tag(TAG).d("getStepSuccess");

        ServiceOrderPhotoStep photoStep = (ServiceOrderPhotoStep) orderStep;
        milestoneEvent = photoStep.getMilestoneWhenFinished();

        stepTitle.setValues(this, orderStep);
        stepDueBy.setValues(this, orderStep);
        photoList.setValues(photoStep.getPhotoRequestList());

        stepTitle.setVisible();
        stepDueBy.setVisible();
        photoList.setVisible();

        //check if we are ok to finish
        if (photoList.readyToFinishStep()) {
            Timber.tag(TAG).d("   ...readyToFinishStep");
            stepComplete.setVisible();
        } else {
            Timber.tag(TAG).d("   ...NOT readyToFinishStep");
            stepComplete.setInvisible();
        }
    }

    public void getStepFailure(){
        Timber.tag(TAG).d("getStepFailure");
    }

    //// photo list interface
    public void photoRequestSelected(PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoRequestSelected -> " + photoRequest.getGuid());
        navigator.gotoActivityPhotoDetail(this, photoRequest.getBatchGuid(), photoRequest.getStepGuid(), photoRequest.getGuid());
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowCompletedServiceOrderAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("active batch -> service order completed!");

        ActiveBatchAlerts alert = new ActiveBatchAlerts();
        alert.showServiceOrderCompletedAlert(this, this);
    }

    public void serviceOrderCompletedAlertHidden() {
        Timber.tag(TAG).d("service order completed alert hidden");
    }


}
