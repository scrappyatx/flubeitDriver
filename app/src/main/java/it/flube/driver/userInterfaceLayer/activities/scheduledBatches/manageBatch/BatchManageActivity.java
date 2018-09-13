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
import it.flube.libbatchdata.builders.BuilderUtilities;
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
        BatchStartAlerts.Response {

    //TODO write layoutComponents for this class

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

    private String activityGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_manage_new);

        controller = new BatchManageController();

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

        activityGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", activityGuid);
    }

    @Override
    public void onResume() {
        super.onResume();

        DrawerMenu.getInstance().setActivity(this, R.string.batch_manage_activity_title);


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
        DrawerMenu.getInstance().clearActivity();
        Timber.tag(TAG).d("onPause (%s)", activityGuid);

        super.onPause();
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("onBackPressed (%s)", activityGuid);
        ActivityNavigator.getInstance().gotoActivityHome(this);
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
        batchDetailTitle.close();
        batchTab.onDestroy();
        batchButtons.close();
        super.onDestroy();

    }


    public void onForfeitClicked(View view){
        Timber.tag(TAG).d("clicked Batch Forfeit");
        batchButtons.forfeitRequestAsk();
        controller.confirmForfeit(this, batchButtons.getBatchGuid(), this);
    }

    public void goBack() {
        ActivityNavigator.getInstance().gotoActivityScheduledBatches(this);
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
    public void useCaseStartBatchSuccess(String batchGuid){
        Timber.tag(TAG).d("useCaseStartBatchSuccess");
        new BatchStartAlerts().showBatchStartedSuccess(this, this);
        // don't set detail visible, keep waiting animation going while we wait for active batch listener to take over
    }

    public void useCaseStartBatchFailure(String batchGuid){
        Timber.tag(TAG).d("useCaseStartBatchFailure");
        new BatchStartAlerts().showBatchStartedFailure(this, this);
        batchTab.setVisible();
        batchButtons.setVisible();
    }

    public void useCaseStartBatchTimeout(String batchGuid){
        Timber.tag(TAG).d("useCaseStartBatchTimeout");
        new BatchStartAlerts().showBatchStartedTimeout(this, this);
        batchTab.setVisible();
        batchButtons.setVisible();
    }

    public void useCaseStartBatchDenied(String batchGuid, String reason){
        Timber.tag(TAG).d("useCaseStartBatchDenied, reason -> " + reason);
        new BatchStartAlerts().showBatchStartedDenied(this, this, reason);
        batchTab.setVisible();
        batchButtons.setVisible();
    }

    ////
    //// BatchStartAlerts.Response
    ////
    public void batchStartAlertHidden(){
        Timber.tag(TAG).d("batchStartAlertHidden");
        //do nothing, listener should pick up that active batch is started and take over
        //or user navigates away

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
        ActivityNavigator.getInstance().gotoActivityScheduledBatchesAndShowBatchForfeitSuccessAlert(this);
    }

    public void forfeitBatchFailure(String batchGuid){
        Timber.tag(TAG).d("forfeitBatchFailure");
        ActivityNavigator.getInstance().gotoActivityScheduledBatchesAndShowBatchForfeitFailureAlert(this);
    }

    public void forfeitBatchTimeout(String batchGuid){
        Timber.tag(TAG).d("forfeitBatchTimeout");
        ActivityNavigator.getInstance().gotoActivityScheduledBatchesAndShowBatchForfeitTimeoutAlert(this);

    }

    public void forfeitBatchDenied(String batchGuid, String reason){
        Timber.tag(TAG).d("forfeitBatchDenied");
        ActivityNavigator.getInstance().gotoActivityScheduledBatchesAndShowBatchForfeitDeniedAlert(this, reason);
    }

    public void forfeitBatchDialogCancelled(String batchGuid){
        //do nothing, user cancelled
        Timber.tag(TAG).d("forfeitBatchCancelled");
        batchButtons.setVisible();
        batchTab.setVisible();
    }



}
