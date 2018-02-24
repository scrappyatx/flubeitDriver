/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowForfeitBatchAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatch.BatchForfeitedEvent;
import timber.log.Timber;

/**
 * Created on 12/18/2017
 * Project : Driver
 */

public class UserInterfaceForfeitBatchEventHandler {
    public final static String TAG = "UserInterfaceForfeitBatchEventHandler";

    private AppCompatActivity activity;
    private ActivityNavigator navigator;

    public UserInterfaceForfeitBatchEventHandler(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator){
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

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(BatchForfeitedEvent event) {
        EventBus.getDefault().removeStickyEvent(BatchForfeitedEvent.class);

        Timber.tag(TAG).d("batch forfeited!");

        EventBus.getDefault().postSticky(new ShowForfeitBatchAlertEvent());
        navigator.gotoActivityScheduledBatches(activity);

    }


}
