/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.scheduledBatches;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tapadoo.alerter.Alerter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.BatchSelectedResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ScheduledBatchesAvailableResponseHandler;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.DrawerMenu;
import timber.log.Timber;

/**
 * Created on 5/28/2017
 * Project : Driver
 */

public class ScheduledBatchesActivity extends AppCompatActivity {
    private static final String TAG = "SchedBatchesActivity";

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

        batchesAdapter = new BatchListAdapter(controller);

        batchesView.setLayoutManager(new LinearLayoutManager(this));
        batchesView.setAdapter(batchesAdapter);
        batchesView.setVisibility(View.INVISIBLE);

        noBatchesText.setVisibility(View.VISIBLE);

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

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ScheduledBatchesAvailableResponseHandler.ScheduledBatchUpdateEvent event) {
        try {
            Timber.tag(TAG).d("received " + Integer.toString(event.getBatchCount()) + " batches");
            if (event.getBatchList().size() > 0) {
                batchesAdapter.updateList(event.getBatchList());
                batchesView.setVisibility(View.VISIBLE);
                noBatchesText.setVisibility(View.INVISIBLE);
            } else {
                batchesView.setVisibility(View.INVISIBLE);
                noBatchesText.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
            batchesView.setVisibility(View.INVISIBLE);
            noBatchesText.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ScheduledBatchesAvailableResponseHandler.NoScheduledBatchesEvent event) {
        batchesView.setVisibility(View.INVISIBLE);
        noBatchesText.setVisibility(View.VISIBLE);
        Timber.tag(TAG).d("No batches available");
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(BatchSelectedResponseHandler.UseCaseBatchSelectedEvent event) {
        Timber.tag(TAG).d("batch selected => " + event.getBatch().getOrderOID());
        navigator.gotoActivityBatchManage(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(BatchManageAlerts.ShowForfeitBatchAlertEvent event) {
        Timber.tag(TAG).d("batch forfeited");
        EventBus.getDefault().removeStickyEvent(event);

        BatchManageAlerts alert = new BatchManageAlerts();
        alert.showForfeitBatchAlert(this);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(BatchManageAlerts.ShowStartedBatchAlertEvent event) {
        Timber.tag(TAG).d("batch started");
        EventBus.getDefault().removeStickyEvent(event);

        BatchManageAlerts alert = new BatchManageAlerts();
        alert.showStartedBatchAlert(this);
    }


}
