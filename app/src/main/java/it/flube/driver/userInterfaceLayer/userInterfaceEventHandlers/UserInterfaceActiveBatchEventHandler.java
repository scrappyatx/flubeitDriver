/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedBatchEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedServiceOrderEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedStepEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedBatchAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowCompletedServiceOrderAlertEvent;
import timber.log.Timber;

/**
 * Created on 12/18/2017
 * Project : Driver
 */

public class UserInterfaceActiveBatchEventHandler {
    public final static String TAG = "UserInterfaceActiveBatchEventHandler";

    private AppCompatActivity activity;
    private ActivityNavigator navigator;

    public UserInterfaceActiveBatchEventHandler(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator){
        this.activity = activity;
        this.navigator = navigator;
        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("created");
    }

    public void close(){
        EventBus.getDefault().unregister(this);
        activity = null;
        navigator = null;
        Timber.tag(TAG).d("closed");
    }

    /// ACTIVE BATCH EVENTS
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchCompletedStepEvent event){
        EventBus.getDefault().removeStickyEvent(ActiveBatchCompletedStepEvent.class);

        Timber.tag(TAG).d("active batch -> step completed!");
        navigator.gotoActiveBatchStep(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchCompletedServiceOrderEvent event){
        EventBus.getDefault().removeStickyEvent(ActiveBatchCompletedServiceOrderEvent.class);

        Timber.tag(TAG).d("active batch -> service order completed!");
        EventBus.getDefault().postSticky(new ShowCompletedServiceOrderAlertEvent());
        navigator.gotoActiveBatchStep(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchCompletedBatchEvent event){
        EventBus.getDefault().removeStickyEvent(ActiveBatchCompletedBatchEvent.class);

        Timber.tag(TAG).d("active batch -> batch completed!");
        EventBus.getDefault().postSticky(new ShowCompletedBatchAlertEvent());
        navigator.gotoActivityHome(activity);
    }

}
