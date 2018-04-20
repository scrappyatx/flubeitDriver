/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches.scheduledBatchList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.userInterfaceLayer.layoutComponents.scheduledBatches.BatchListAdapter;
import it.flube.driver.userInterfaceLayer.layoutComponents.scheduledBatches.BatchListLayoutComponents;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchListUpdateEvent;
import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.drawerMenu.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class ScheduledBatchesActivity extends AppCompatActivity implements
        BatchListAdapter.Response {

    private static final String TAG = "ScheduledBatchesActivity";

    private ScheduledBatchesController controller;
    private ActivityNavigator navigator;
    private DrawerMenu drawer;

    private BatchListLayoutComponents batchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scheduled_batches);
        batchList = new BatchListLayoutComponents(this, getString(R.string.scheduled_batches_no_batches));

        Timber.tag(TAG).d("onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();

        navigator = new ActivityNavigator();
        drawer = new DrawerMenu(this, navigator, R.string.scheduled_batches_activity_title);
        controller = new ScheduledBatchesController();

        EventBus.getDefault().register(this);

        batchList.onResume(this, this);
        batchList.setValues(AndroidDevice.getInstance().getOfferLists().getScheduledBatches());
        batchList.setVisible();

        controller.checkIfForfeitAlertNeedsToBeShown(this);

        Timber.tag(TAG).d("onResume");
    }

    @Override
    public void onPause() {

        EventBus.getDefault().unregister(this);

        drawer.close();
        controller.close();
        batchList.onPause();


        Timber.tag(TAG).d( "onPause");
        super.onPause();
    }

    public void batchSelected(Batch batch){
        Timber.tag(TAG).d("...batchSelected -> " + batch.getGuid());
        navigator.gotoActivityBatchManage(this, batch.getGuid());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ScheduledBatchListUpdateEvent event) {
        Timber.tag(TAG).d("received ScheduledBatchListUpdateEvent");
        batchList.setValues(AndroidDevice.getInstance().getOfferLists().getScheduledBatches());
        batchList.setVisible();
    }


}
