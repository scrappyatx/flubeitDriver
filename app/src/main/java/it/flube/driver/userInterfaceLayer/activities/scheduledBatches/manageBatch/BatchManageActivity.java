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

import it.flube.driver.R;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.BatchSelectedResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ForfeitBatchResponseHandler;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabDetails.TabDetailLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.BatchDetailTitleLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchDetail.batchDetailTab.tabLocations.TabLocationsLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.batchManage.BatchManageLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowForfeitBatchAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.demoBatch.DemoBatchStartedEvent;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class BatchManageActivity extends AppCompatActivity
    implements SlideView.OnSlideCompleteListener {

    private static final String TAG = "BatchManageActivity";

    private ActivityNavigator navigator;
    private BatchManageController controller;
    private DrawerMenu drawer;

    private BatchDetail batchDetail;
    private BatchDetailTitleLayoutComponents batchDetailTitle;
    private TabDetailLayoutComponents batchDetailTab;
    private TabLocationsLayoutComponents batchLocationsTab;
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


        //tab contents
        batchDetailTab = new TabDetailLayoutComponents(this, false);
        batchLocationsTab = new TabLocationsLayoutComponents(this,savedInstanceState);

        //buttons
        batchButtons = new BatchManageLayoutComponents(this, this);

        //set all viewgroups GONE
        batchDetailTitle.setInvisible();
        batchDetailTab.setInvisible();
        batchLocationsTab.setInvisible();
        batchButtons.setInvisible();

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();


        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.batch_manage_activity_title);
        controller = new BatchManageController();

        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        Timber.tag(TAG).d(TAG, "onPause");

        super.onPause();
    }

    public void onForfeitClicked(View view){
        Timber.tag(TAG).d("clicked Batch Forfeit");
        controller.confirmForfeit(this, batchDetail);
    }

    public void goBack() {
        navigator.gotoActivityScheduledBatches(this);
    }

    public void goMap() {
        navigator.gotoActivityBatchMap(this);
    }


    // slide button listener
    public void onSlideComplete(SlideView slideView) {
        batchButtons.batchStarted();
        controller.startDemoBatch(batchDetail);
        Timber.tag(TAG).d("Starting batch --> " + batchDetail.getBatchGuid());

        //goMap();
    }


    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(BatchSelectedResponseHandler.UseCaseBatchSelectedEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("*** Offer was selected event");
        batchDetail = event.getBatchDetail();

        batchDetailTitle.setValues(this, batchDetail);
        batchDetailTitle.setVisible();

        batchDetailTab.setValues(this, batchDetail);
        batchDetailTab.setVisible();

        batchButtons.setValuesAndShow();
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ForfeitBatchResponseHandler.UseCaseForfeitBatchEvent event) {
        Timber.tag(TAG).d("UseCaseForfeitBatchEvent received --> " + event.getBatchGuid());
        EventBus.getDefault().removeStickyEvent(event);
        EventBus.getDefault().postSticky(new ShowForfeitBatchAlertEvent());
        navigator.gotoActivityScheduledBatches(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(DemoBatchStartedEvent event) {
        Timber.tag(TAG).d("DemoBatchEvent received");
        EventBus.getDefault().removeStickyEvent(event);
        //EventBus.getDefault().postSticky(new ShowStartedBatchAlertEvent());
        //navigator.gotoActivityBatchMap(this);
        //TODO implement batch started alert event cleanly
    }


}
