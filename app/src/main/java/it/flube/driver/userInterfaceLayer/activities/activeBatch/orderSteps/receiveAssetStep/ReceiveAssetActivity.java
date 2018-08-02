/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents.ReceiveAssetLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailCompleteButtonComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 4/27/2018
 * Project : Driver
 */
public class ReceiveAssetActivity extends AppCompatActivity implements
        ReceiveAssetController.GetDriverAndActiveBatchStepResponse,
        SlideView.OnSlideCompleteListener,
        ActiveBatchAlerts.ServiceOrderCompletedAlertHidden {

    private static final String TAG = "ReceiveAssetActivity";

    private ActivityNavigator navigator;
    private ReceiveAssetController controller;
    private DrawerMenu drawer;

    private ReceiveAssetLayoutComponents layoutComponents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receive_asset_step);

        layoutComponents = new ReceiveAssetLayoutComponents(this,getResources().getString(R.string.receive_asset_step_completed_step_button_caption), this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.receive_asset_step_activity_title);
        controller = new ReceiveAssetController();
    }

    @Override
    public void onResume() {
        super.onResume();
        controller.getDriverAndActiveBatchStep(this);

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

    @Override
    public void onStop(){
        Timber.tag(TAG).d("onStop");
        drawer.close();
        controller.close();
        super.onStop();
    }

    ///
    /// when user clicks asset transfer items
    ///

    public void gotoAssetList(View v){
        Timber.tag(TAG).d("gotoAssetList");
    }

    ////
    /// when user clicks signature
    ////
    public void gotoGetSignature(View v){
        Timber.tag(TAG).d("gotoGetSignature");
    }

    ///
    /// when user clicks call contact button
    ///
    public void clickContactCallButton(View v){
        Timber.tag(TAG).d("clickContactCallButton");
    }

    /// when user clicks text contact button
    ///
    ///
    public void clickContactTextButton(View v){
        Timber.tag(TAG).d("clickContactTextButton");
    }

    /// when user clicks APP_INFO button
    ///
    ///
    public void clickSettings(View v){
        Timber.tag(TAG).d("clickSettings");
    }


    ///
    /// OnSlideCompleteListener
    ///
    public void onSlideComplete(SlideView v){
        Timber.tag(TAG).d("clicked step complete button");
        layoutComponents.showWaitingAnimationAndBanner(this);
        controller.stepFinished(layoutComponents.getOrderStep().getMilestoneWhenFinished());
    }

    ///
    ///  ReceiveAssetController.GetDriverAndActiveBatchStepResponse interface
    ///
    public void gotDriverAndStep(Driver driver, BatchDetail batchDetail, ServiceOrder serviceOrder, ServiceOrderReceiveAssetStep orderStep){
        Timber.tag(TAG).d("gotDriverAndStep");
        layoutComponents.setValues(this,orderStep);
        /// TODO check call permission
        layoutComponents.setVisible(true);
    }

    public void gotNoDriver(){
        Timber.tag(TAG).d("gotNoDriver");
        navigator.gotoActivityLogin(this);
    }

    public void gotDriverButNoStep(Driver driver){
        Timber.tag(TAG).d("gotDriverButNoStep");
        navigator.gotoActivityHome(this);
    }

    public void gotStepMismatch(Driver driver, OrderStepInterface.TaskType taskType){
        Timber.tag(TAG).d("gotStepMismatch, taskType -> " + taskType.toString());
        navigator.gotoActiveBatchStep(this);
    }

    ////
    ////  EventBus events
    ////

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
