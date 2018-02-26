/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.BatchSelectedResponseHandler;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowForfeitBatchAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowStartedBatchAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchListUpdateEvent;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import it.flube.driver.userInterfaceLayer.activities.scheduledBatches.manageBatch.BatchManageAlerts;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class ScheduledBatchesActivity extends AppCompatActivity implements
        BatchManageAlerts.ForfeitBatchAlertHidden,
        BatchManageAlerts.StartedBatchAlertHidden {

    private static final String TAG = "ScheduledBatchesActivity";

    private ScheduledBatchesController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private RecyclerView batchesView;
    private BatchListAdapter batchesAdapter;
    private TextView noBatchesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scheduled_batches);
        batchesView = (RecyclerView) findViewById(R.id.scheduledBatchesView);
        noBatchesText = (TextView) findViewById(R.id.noBatches);

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.scheduled_batches_activity_title);
        controller = new ScheduledBatchesController();

        batchesAdapter = new BatchListAdapter(this, controller);

        batchesView.setLayoutManager(new LinearLayoutManager(this));
        batchesView.setAdapter(batchesAdapter);
        batchesView.setVisibility(View.INVISIBLE);

        noBatchesText.setVisibility(View.VISIBLE);

        EventBus.getDefault().register(this);
        updateScheduledBatchList();

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        batchesAdapter.close();

        Timber.tag(TAG).d(TAG, "onPause");
        super.onPause();
    }

    private void updateScheduledBatchList(){
        ArrayList<Batch> batchList = AndroidDevice.getInstance().getOfferLists().getScheduledBatches();
        Integer batchCount = batchList.size();

        if (batchCount > 0) {


            Timber.tag(TAG).d("updating list!");
            batchesAdapter.updateList(batchList);
            batchesView.setVisibility(View.VISIBLE);
            noBatchesText.setVisibility(View.INVISIBLE);
        } else {
            Timber.tag(TAG).d("making list invisible!");
            batchesView.setVisibility(View.INVISIBLE);
            noBatchesText.setVisibility(View.VISIBLE);
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ScheduledBatchListUpdateEvent event) {
        Timber.tag(TAG).d("received ScheduledBatchListUpdateEvent");
        updateScheduledBatchList();
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(BatchSelectedResponseHandler.UseCaseBatchSelectedEvent event) {
        Timber.tag(TAG).d("batch selected => " + event.getBatchDetail().getBatchGuid());
        navigator.gotoActivityBatchManage(this, event.getBatchDetail().getBatchGuid());
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowForfeitBatchAlertEvent event) {
        Timber.tag(TAG).d("batch forfeited");
        EventBus.getDefault().removeStickyEvent(event);

        BatchManageAlerts alert = new BatchManageAlerts();
        alert.showForfeitBatchAlert(this, this);
    }

    public void forfeitBatchAlertHidden() {
        Timber.tag(TAG).d("forfeitBatchAlert hidden");
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ShowStartedBatchAlertEvent event) {
        Timber.tag(TAG).d("batch started");
        EventBus.getDefault().removeStickyEvent(event);

        BatchManageAlerts alert = new BatchManageAlerts();
        alert.showStartedBatchAlert(this, this);
    }

    public void startedBatchAlertHidden(){
        Timber.tag(TAG).d("startedBatchAlert hidden");
    }

}
