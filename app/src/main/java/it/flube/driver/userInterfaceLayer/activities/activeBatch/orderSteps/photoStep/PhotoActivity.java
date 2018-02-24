/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.PhotoRequest;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;
import it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.StepDetailCompleteLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.StepDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.photoList.PhotoRequestListAdapter;
import it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.photoList.PhotoRequestListLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderPhotoStep;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 10/26/2017
 * Project : Driver
 */

public class PhotoActivity extends AppCompatActivity implements
        ActiveBatchAlerts.ServiceOrderCompletedAlertHidden,
        PhotoRequestListAdapter.Response {

    private static final String TAG = "PhotoActivity";

    private ActivityNavigator navigator;
    private PhotoController controller;
    private DrawerMenu drawer;

    private StepDetailTitleLayoutComponents stepTitle;
    private StepDetailDueByLayoutComponents stepDueBy;
    private PhotoRequestListLayoutComponents photoList;
    private StepDetailCompleteLayoutComponents stepComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_step);

        stepTitle = new StepDetailTitleLayoutComponents(this);
        stepDueBy = new StepDetailDueByLayoutComponents(this);
        photoList = new PhotoRequestListLayoutComponents(this, this);
        stepComplete = new StepDetailCompleteLayoutComponents(this, getResources().getString(R.string.photo_step_completed_step_button_caption));

    }

    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.photo_step_activity_title);
        controller = new PhotoController();

        updateValues();

        stepTitle.setVisible();
        stepDueBy.setVisible();
        photoList.setVisible();
        stepComplete.setVisible();

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

    private void updateValues(){
        Timber.tag(TAG).d("updating Values...");
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            ServiceOrderPhotoStep step = AndroidDevice.getInstance().getActiveBatch().getPhotoStep();
            OrderStepInterface oiStep = AndroidDevice.getInstance().getActiveBatch().getStep();

            stepTitle.setValues(this, oiStep);
            stepDueBy.setValues(this, oiStep);
            photoList.setValues(step.getPhotoRequestList());

            Timber.tag(TAG).d("...photoList update complete");
        } else {
            Timber.tag(TAG).d("...can't update photoList, no active batch");
        }
    }


    public void clickStepCompleteButton(View v){
        Timber.tag(TAG).d("clicked step complete button");

        stepComplete.buttonWasClicked();

        String milestoneEvent;
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            ServiceOrderPhotoStep step = AndroidDevice.getInstance().getActiveBatch().getPhotoStep();
            milestoneEvent = step.getMilestoneWhenFinished();
        } else {
            milestoneEvent = "no milestone";
        }
        controller.stepFinished(milestoneEvent);
    }

    //// photo list interface
    public void photoRequestSelected(PhotoRequest photoRequest){
        Timber.tag(TAG).d("photoRequestSelected -> " + photoRequest.getGuid());


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
