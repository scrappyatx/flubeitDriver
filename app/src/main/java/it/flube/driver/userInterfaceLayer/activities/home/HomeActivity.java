/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.layoutComponents.homeSummary.ActiveBatchSummaryLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.homeSummary.DemoOffersSummaryLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.homeSummary.PersonalOffersSummaryLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.homeSummary.PublicOffersSummaryLayoutComponents;
import it.flube.driver.userInterfaceLayer.layoutComponents.homeSummary.ScheduledBatchesSummaryLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedBatchAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOffersUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOffersUpdatedEvent;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.PermissionsCheckActivity;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchCountUpdateEvent;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class HomeActivity extends PermissionsCheckActivity implements
        ActiveBatchAlerts.BatchCompletedAlertHidden {

    private static final String TAG = "HomeActivity";

    private HomeController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private ActiveBatchSummaryLayoutComponents activeBatch;
    private ScheduledBatchesSummaryLayoutComponents scheduledBatches;

    private PublicOffersSummaryLayoutComponents publicOffers;
    private PersonalOffersSummaryLayoutComponents personalOffers;
    private DemoOffersSummaryLayoutComponents demoOffers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        activeBatch = new ActiveBatchSummaryLayoutComponents(this);
        scheduledBatches = new ScheduledBatchesSummaryLayoutComponents(this);
        publicOffers = new PublicOffersSummaryLayoutComponents(this);
        personalOffers = new PersonalOffersSummaryLayoutComponents(this);
        demoOffers = new DemoOffersSummaryLayoutComponents(this);

        activeBatch.setGone();
        scheduledBatches.setGone();
        publicOffers.setGone();
        personalOffers.setGone();
        demoOffers.setGone();

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onPermissionResume(){
        super.onPermissionResume();
        Timber.tag(TAG).d("onPermissionResume");
        EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.home_no_active_batch_activity_title);
        controller = new HomeController();

        /// update active batch
        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()) {
            activeBatch.setActiveBatch(AndroidDevice.getInstance().getActiveBatch().getBatchDetail());
        } else {
            activeBatch.setNoActiveBatch(this);
        }

        /// update scheduled batches
        scheduledBatches.setValuesAndShow(this, AndroidDevice.getInstance().getOfferLists().getScheduledBatches().size());

        // update public offers
        publicOffers.setValuesAndShow(this, AndroidDevice.getInstance().getOfferLists().getPublicOffers().size());

        //update personal offers
        personalOffers.setValuesAndShow(this,AndroidDevice.getInstance().getOfferLists().getPersonalOffers().size());

        //update demo offers
        demoOffers.setValuesAndShow(this, AndroidDevice.getInstance().getOfferLists().getDemoOffers().size());

    }


    @Override
    public void onPermissionPause(){
        super.onPermissionPause();
        Timber.tag(TAG).d("onPermissionPause");
        EventBus.getDefault().unregister(this);

        drawer.close();
    }

    @Override
    public void onBackPressed(){
        Timber.tag(TAG).d("back button pressed");
    }

    /// button click handlers
    public void onClickActiveBatch(View view){
        navigator.gotoActiveBatchStep(this);
        Timber.tag(TAG).d("clicked Active Batch button");
    }

    public void onClickScheduledBatches(View view){
        navigator.gotoActivityScheduledBatches(this);
        Timber.tag(TAG).d("clicked Scheduled Batches button");
    }

    public void onClickPublicOffers(View view){
        navigator.gotoActivityPublicOffers(this);
        Timber.tag(TAG).d("clicked publicOffers button");
    }

    public void onClickPersonalOffers(View view){
        navigator.gotoActivityPublicOffers(this);
        Timber.tag(TAG).d("clicked Personal Offers button");
    }

    public void onClickDemoOffers(View view){
        navigator.gotoActivityDemoOffers(this);
        Timber.tag(TAG).d("clicked Demo Offers button");
    }

    /// scheduled batches
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ScheduledBatchCountUpdateEvent event) {
        Timber.tag(TAG).d("received scheduled batch count update event");
        scheduledBatches.setValuesAndShow(this, AndroidDevice.getInstance().getOfferLists().getScheduledBatches().size());
    }

    /// public offers events
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received public offer count update event");
        publicOffers.setValuesAndShow(this, AndroidDevice.getInstance().getOfferLists().getPublicOffers().size());
    }

    /// personal offers events
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PersonalOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received personal offer count update event");
        personalOffers.setValuesAndShow(this, AndroidDevice.getInstance().getOfferLists().getPersonalOffers().size());
    }

    /// demo offers events
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received demo offer count update event");
        demoOffers.setValuesAndShow(this, AndroidDevice.getInstance().getOfferLists().getDemoOffers().size());
    }


    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowCompletedBatchAlertEvent event) {
        EventBus.getDefault().removeStickyEvent(event);

        Timber.tag(TAG).d("active batch -> batch completed!");

        ActiveBatchAlerts alert = new ActiveBatchAlerts();
        alert.showBatchCompletedAlert(this, this);
    }

    public void batchCompletedAlertHidden() {
        Timber.tag(TAG).d("batch completed alert hidden");
    }


}
