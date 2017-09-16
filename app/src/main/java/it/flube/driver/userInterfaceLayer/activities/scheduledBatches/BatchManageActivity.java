/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.BatchSelectedResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ForfeitBatchResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.StartDemoBatchResponseHandler;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 7/24/2017
 * Project : Driver
 */

public class BatchManageActivity extends AppCompatActivity {
    private static final String TAG = "BatchManageActivity";

    private ActivityNavigator navigator;
    private BatchManageController controller;
    private DrawerMenu drawer;

    private BatchCloudDB batch;

    private TextView batchDescription;
    private TextView batchPickupStreet;
    private TextView batchPickupCityStateZip;
    private TextView batchReturnStreet;
    private TextView batchReturnCityStateZip;
    private TextView batchForfeit;
    private SlideView batchStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_manage);

        batchDescription = (TextView) findViewById(R.id.batch_manage_description);
        batchPickupStreet = (TextView) findViewById(R.id.batch_manage_pickup_street1);
        batchPickupCityStateZip = (TextView) findViewById(R.id.batch_manage_pickup_city_state_zip);
        batchReturnStreet = (TextView) findViewById(R.id.batch_manage_return_street1);
        batchReturnCityStateZip = (TextView) findViewById(R.id.batch_manage_return_city_state_zip);
        batchForfeit = (TextView) findViewById(R.id.batch_manage_forfeit);

        batchStart = (SlideView) findViewById(R.id.batch_manage_start);
        batchStart.setOnSlideCompleteListener(new BatchStartListener());

        batchStart.setVisibility(View.INVISIBLE);
        batchForfeit.setVisibility(View.INVISIBLE);

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
        controller.confirmForfeit(this, batch);
    }

    public void goBack() {
        navigator.gotoActivityScheduledBatches(this);
    }

    public void goMap() {
        navigator.gotoActivityBatchMap(this);
    }

    private class BatchStartListener implements SlideView.OnSlideCompleteListener {
        public void onSlideComplete(SlideView slideView) {
            controller.startDemoBatch(batch);
            Timber.tag(TAG).d("Starting batch --> " + batch.getOrderOID());

            //goMap();
        }
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(BatchSelectedResponseHandler.UseCaseBatchSelectedEvent event) {
        batch = event.getBatch();


        String pickupCSZ = batch.getPickupLocation().getCity() + ", " + batch.getPickupLocation().getState() + " " + batch.getPickupLocation().getZip();
        String dropoffCSZ = batch.getPickupLocation().getCity() + ", " + batch.getPickupLocation().getState() + " " + batch.getPickupLocation().getZip();

        Timber.tag(TAG).d("batch selected => " + batch.getOrderOID());
        Timber.tag(TAG).d("---> Description    : " + batch.getOrderOID());
        Timber.tag(TAG).d("---> Pickup Street  : " + batch.getPickupLocation().getStreet1());
        Timber.tag(TAG).d("---> Pickup CSZ     : " + pickupCSZ);
        Timber.tag(TAG).d("---> Dropoff Street : " + batch.getReturnLocation().getStreet1());
        Timber.tag(TAG).d("---> Dropoff CSZ    : " + dropoffCSZ);

        batchDescription.setText(batch.getOrderOID());
        batchPickupStreet.setText(batch.getPickupLocation().getStreet1());
        batchPickupCityStateZip.setText(pickupCSZ);
        batchReturnStreet.setText(batch.getReturnLocation().getStreet1());
        batchReturnCityStateZip.setText(dropoffCSZ);

        batchStart.setVisibility(View.VISIBLE);
        batchForfeit.setVisibility(View.VISIBLE);
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ForfeitBatchResponseHandler.UseCaseForfeitBatchEvent event) {
        Timber.tag(TAG).d("UseCaseForfeitBatchEvent received --> " + event.getBatch().getOrderOID());
        EventBus.getDefault().removeStickyEvent(event);
        EventBus.getDefault().postSticky(new BatchManageAlerts.ShowForfeitBatchAlertEvent());
        navigator.gotoActivityScheduledBatches(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(StartDemoBatchResponseHandler.UseCaseStartDemoBatchEvent event) {
        Timber.tag(TAG).d("UseCaseStartDemoBatchEvent received --> " + event.getBatch().getGUID());
        EventBus.getDefault().removeStickyEvent(event);
        EventBus.getDefault().postSticky(new BatchManageAlerts.ShowStartedBatchAlertEvent());
        navigator.gotoActivityBatchMap(this);
    }


}
