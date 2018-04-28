/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.userTriggerStep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.ReceiveAssetController;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.StepDetailCompleteLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.orderStep.StepDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderUserTriggerStep;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 4/28/2018
 * Project : Driver
 */
public class UserTriggerActivity extends AppCompatActivity implements
        ActiveBatchAlerts.ServiceOrderCompletedAlertHidden {

    private static final String TAG = "UserTriggerActivity";

    private ActivityNavigator navigator;
    private UserTriggerController controller;
    private DrawerMenu drawer;

    private StepDetailTitleLayoutComponents stepTitle;
    private StepDetailDueByLayoutComponents stepDueBy;
    private StepDetailCompleteLayoutComponents stepComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_trigger_step);

        stepTitle = new StepDetailTitleLayoutComponents(this);
        stepDueBy = new StepDetailDueByLayoutComponents(this);
        stepComplete = new StepDetailCompleteLayoutComponents(this, getResources().getString(R.string.user_trigger_step_completed_step_button_caption));

    }

    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.user_trigger_step_activity_title);
        controller = new UserTriggerController();

        updateValues();

        stepTitle.setVisible();
        stepDueBy.setVisible();
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
            ServiceOrderUserTriggerStep step = AndroidDevice.getInstance().getActiveBatch().getUserTriggerStep();
            OrderStepInterface oiStep = AndroidDevice.getInstance().getActiveBatch().getStep();

            stepTitle.setValues(this, oiStep);
            stepDueBy.setValues(this, oiStep);

            Timber.tag(TAG).d("...values update complete");
        } else {
            Timber.tag(TAG).d("...no active batch");
        }
    }


    public void clickStepCompleteButton(View v){
        Timber.tag(TAG).d("clicked step complete button");

        stepComplete.buttonWasClicked();

        String milestoneEvent;
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            ServiceOrderUserTriggerStep step = AndroidDevice.getInstance().getActiveBatch().getUserTriggerStep();
            milestoneEvent = step.getMilestoneWhenFinished();
        } else {
            milestoneEvent = "no milestone";
        }
        controller.stepFinished(milestoneEvent);
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
