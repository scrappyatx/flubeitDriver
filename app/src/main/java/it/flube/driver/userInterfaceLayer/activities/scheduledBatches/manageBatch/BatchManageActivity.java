/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.useCaseLayer.manageBatch.UseCaseStartBatchRequest;
import it.flube.driver.useCaseLayer.manageBatch.getBatchData.UseCaseGetBatchData;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.BatchDetailTabLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails.TabDetailLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.BatchDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabLocations.TabLocationsLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchManage.BatchManageLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.demoBatch.DemoBatchStartedEvent;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import ng.max.slideview.SlideView;
import timber.log.Timber;

import static it.flube.driver.userInterfaceLayer.activities.scheduledBatches.BatchConstants.BATCH_GUID_KEY;


/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class BatchManageActivity extends AppCompatActivity
    implements
        UseCaseGetBatchData.Response,
        ForfeitBatchConfirmation.Response,
        UseCaseStartBatchRequest.Response,
        SlideView.OnSlideCompleteListener,
        BatchManageAlerts.StartedBatchAlertHidden {

    private static final String TAG = "BatchManageActivity";

    private ActivityNavigator navigator;
    private BatchManageController controller;
    private DrawerMenu drawer;

    private BatchDetailTitleLayoutComponents batchDetailTitle;
    private BatchDetailTabLayoutComponents batchTab;
    private BatchManageLayoutComponents batchButtons;

    // forfeit offer & swipe to start button
    private LottieAnimationView batchStartWaitingAnimation;
    private TextView batchForfeit;
    private SlideView batchStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_manage_new);

        //title block
        batchDetailTitle = new BatchDetailTitleLayoutComponents(this);

        //tab selector
        batchTab = new BatchDetailTabLayoutComponents(this, savedInstanceState, false);

        //buttons
        batchButtons = new BatchManageLayoutComponents(this, this);

        //set all viewgroups GONE
        batchDetailTitle.setGone();
        batchTab.setGone();
        batchButtons.setGone();

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();


        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.batch_manage_activity_title);
        controller = new BatchManageController();

        /// get the batch data for the batchGuid that was used to launch this activity
        if (getIntent().hasExtra(BATCH_GUID_KEY)){
            //get the batchGuid
            String batchGuid = getIntent().getStringExtra(BatchConstants.BATCH_GUID_KEY);
            Timber.tag(TAG).d("batchGuid -> " + batchGuid);
            controller.getBatchData(batchGuid,this);
        } else {
            Timber.tag(TAG).w("no batchGuid intent, this should never happen");
        }

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        drawer.close();
        controller.close();
        Timber.tag(TAG).d( "onPause");

        super.onPause();
    }

    public void onForfeitClicked(View view){
        Timber.tag(TAG).d("clicked Batch Forfeit");
        batchButtons.forfeitRequestAsk();
        controller.confirmForfeit(this, batchButtons.getBatchGuid(), this);
    }

    public void goBack() {
        navigator.gotoActivityScheduledBatches(this);
    }


    // slide button listener
    public void onSlideComplete(SlideView slideView) {
        batchTab.setGone();
        batchButtons.batchStarted();
        controller.startBatch(batchButtons.getBatchGuid(), this);
        Timber.tag(TAG).d("Starting batch --> " + batchButtons.getBatchGuid());

    }

    ////
    ///     UseCaseGetBatchData Response interface
    ///
    public void getBatchDataSuccess(BatchDetail batchDetail, ArrayList<ServiceOrder> orderList, ArrayList<RouteStop> routeList){
        Timber.tag(TAG).d("getBatchDataSuccess");

        batchDetailTitle.setValues(this, batchDetail);
        batchDetailTitle.setVisible();

        batchTab.setValues(this, batchDetail, orderList, routeList);
        batchTab.setVisible();

        batchButtons.setValuesAndShow(batchDetail.getBatchGuid());
    }

    public void getBatchDataFailure(){
        Timber.tag(TAG).d("getBatchDataFailure");
    }

    ////
    ////    UseCaseStartBatchRequest response
    ////
    public void useCaseStartBatchComplete(String batchGuid){
        Timber.tag(TAG).d("startBatchComplete");
        new BatchManageAlerts().showStartedBatchAlert(this, this);
        //do nothing, listener should pick up that active batch is started and take over

    }

    public void startedBatchAlertHidden(){
        Timber.tag(TAG).d("startedBatchAlertHidden");
    }

    ////
    ////    ForfeitBatchConfirmation response
    ////

    public void forfeitBatchDialogMakeForfeitRequest(String batchGuid){
        Timber.tag(TAG).d("forfeitBatchDialogMakeForfeitRequest");
        batchTab.setGone();
        batchButtons.forfeitRequestStart();
    }

    public void forfeitBatchSuccess(String batchGuid){
        Timber.tag(TAG).d("forfeitBatchSuccess");
        navigator.gotoActivityScheduledBatchesAndShowBatchForfeitSuccessAlert(this);
    }

    public void forfeitBatchFailure(String batchGuid){
        Timber.tag(TAG).d("forfeitBatchFailure");
        navigator.gotoActivityScheduledBatchesAndShowBatchForfeitFailureAlert(this);
    }

    public void forfeitBatchTimeout(String batchGuid){
        Timber.tag(TAG).d("forfeitBatchTimeout");
        navigator.gotoActivityScheduledBatchesAndShowBatchForfeitTimeoutAlert(this);

    }

    public void forfeitBatchDenied(String batchGuid, String reason){
        Timber.tag(TAG).d("forfeitBatchDenied");
        navigator.gotoActivityScheduledBatchesAndShowBatchForfeitDeniedAlert(this, reason);
    }

    public void forfeitBatchDialogCancelled(String batchGuid){
        //do nothing, user cancelled
        Timber.tag(TAG).d("forfeitBatchCancelled");
        batchButtons.setVisible();
        batchTab.setVisible();
    }



}
