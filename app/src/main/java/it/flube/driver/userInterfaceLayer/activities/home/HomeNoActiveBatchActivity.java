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
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.personalOffers.PersonalOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers.PublicOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ScheduledBatchesAvailableResponseHandler;
import it.flube.driver.dataLayer.userInterfaceEvents.batchAlerts.ShowCompletedBatchAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PersonalOffersUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PublicOffersUpdatedEvent;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.ActiveBatchAlerts;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.PermissionsCheckActivity;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class HomeNoActiveBatchActivity extends PermissionsCheckActivity implements
        ActiveBatchAlerts.BatchCompletedAlertHidden {

    private static final String TAG = "HomeNabActivity";

    private HomeNoActiveBatchController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private ConstraintLayout activeBatchLayout;
    private ConstraintLayout scheduledBatchesLayout;
    private ConstraintLayout publicOffersLayout;
    private ConstraintLayout personalOffersLayout;

    private TextView activeBatchDetail;
    private TextView scheduledBatchesDetail;
    private TextView publicOffersDetail;
    private TextView personalOffersDetail;

    private Button activeBatchButton;
    private Button scheduledBatchesButton;
    private Button publicOffersButton;
    private Button personalOffersButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_no_active_batch);

        activeBatchLayout = (ConstraintLayout) findViewById(R.id.summary_active_batch_view);
        scheduledBatchesLayout = (ConstraintLayout) findViewById(R.id.summary_scheduled_batches_view);
        publicOffersLayout = (ConstraintLayout) findViewById(R.id.summary_public_offers_view);
        personalOffersLayout = (ConstraintLayout) findViewById(R.id.summary_personal_offers_view);

        activeBatchDetail = (TextView)findViewById(R.id.summary_active_batch_detail);
        scheduledBatchesDetail = (TextView)findViewById(R.id.summary_scheduled_batches_detail);
        publicOffersDetail = (TextView) findViewById(R.id.summary_public_offers_detail);
        personalOffersDetail = (TextView) findViewById(R.id.summary_personal_offers_detail);

        activeBatchLayout.setVisibility(View.GONE);
        scheduledBatchesLayout.setVisibility(View.VISIBLE);
        publicOffersLayout.setVisibility(View.VISIBLE);
        personalOffersLayout.setVisibility(View.VISIBLE);

        activeBatchButton = (Button) findViewById(R.id.summary_active_batch_button);

        scheduledBatchesButton = (Button) findViewById(R.id.summary_scheduled_batches_button);
        scheduledBatchesButton.setOnClickListener(new scheduledBatchesOnClickListener());

        publicOffersButton = (Button) findViewById(R.id.summary_public_offers_button);
        publicOffersButton.setOnClickListener(new publicOffersOnClickListener());

        personalOffersButton = (Button) findViewById(R.id.summary_personal_offers_button);
        personalOffersButton.setOnClickListener(new personalOffersOnClickListener());


        activeBatchButton.setVisibility(View.GONE);
        scheduledBatchesButton.setVisibility(View.GONE);
        publicOffersButton.setVisibility(View.GONE);
        personalOffersButton.setVisibility(View.GONE);

        personalOffersDetail.setText("There are no personal offers available.");


        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onPermissionResume(){
        super.onPermissionResume();
        Timber.tag(TAG).d("onPermissionResume");
        EventBus.getDefault().register(this);

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.home_no_active_batch_activity_title);
        controller = new HomeNoActiveBatchController();

        controller.sendSomeTestMessages();
    }


    @Override
    public void onPermissionPause(){
        super.onPermissionPause();
        Timber.tag(TAG).d("onPermissionPause");
        EventBus.getDefault().unregister(this);

        drawer.close();
    }

    /// scheduled batches button click handler

    private class scheduledBatchesOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            navigator.gotoActivityScheduledBatches(HomeNoActiveBatchActivity.this);
            Timber.tag(TAG).d("clicked scheduledBatches button");
        }
    }

    /// public offers button click handler
    private class publicOffersOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            navigator.gotoActivityPublicOffers(HomeNoActiveBatchActivity.this);
            Timber.tag(TAG).d("clicked publicOffers button");
        }
    }


    /// personal offers button click hander
    private class personalOffersOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            Timber.tag(TAG).d("clicked personalOffers button");
        }
    }

    /// public offers events
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOffersUpdatedEvent event) {
        try {
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " offers");
            if (event.getOfferList().size() > 0) {
                publicOffersDetail.setText("There are " + event.getOfferCount() + " public offers available!");
                publicOffersButton.setVisibility(View.VISIBLE);
            } else {
                publicOffersDetail.setText("There are no public offers available");
                publicOffersButton.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            publicOffersDetail.setText("There are no public offers available");
            publicOffersButton.setVisibility(View.GONE);
        }
    }

    // personal offers events
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(PersonalOffersUpdatedEvent event) {
        try {
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " offers");
            if (event.getOfferList().size() > 0) {
                personalOffersDetail.setText("There are " + event.getOfferCount() + " personal offers available!");
                personalOffersButton.setVisibility(View.VISIBLE);
            } else {
                personalOffersDetail.setText("There are no personal offers available");
                personalOffersButton.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            personalOffersDetail.setText("There are no personal offers available");
            personalOffersButton.setVisibility(View.GONE);
        }
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
