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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedBatchEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedServiceOrderEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchCompletedStepEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchFinishedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchRemovedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchWaitingToFinishEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedNoBatchEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedStepStartedEvent;
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


    public UserInterfaceActiveBatchEventHandler(@NonNull AppCompatActivity activity){
        this.activity = activity;
        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("created");
    }

    public void close(){
        EventBus.getDefault().unregister(this);
        activity = null;
        Timber.tag(TAG).d("closed");
    }

    /// ACTIVE BATCH EVENTS
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchCompletedStepEvent event){
        EventBus.getDefault().removeStickyEvent(ActiveBatchCompletedStepEvent.class);

        Timber.tag(TAG).d("active batch -> step completed!");
        ActivityNavigator.getInstance().gotoActiveBatchStep(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchCompletedServiceOrderEvent event){
        EventBus.getDefault().removeStickyEvent(ActiveBatchCompletedServiceOrderEvent.class);

        Timber.tag(TAG).d("active batch -> service order completed!");
        EventBus.getDefault().postSticky(new ShowCompletedServiceOrderAlertEvent());
        ActivityNavigator.getInstance().gotoActiveBatchStep(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchCompletedBatchEvent event){
        EventBus.getDefault().removeStickyEvent(ActiveBatchCompletedBatchEvent.class);

        Timber.tag(TAG).d("active batch -> batch completed!");
        EventBus.getDefault().postSticky(new ShowCompletedBatchAlertEvent());
        ActivityNavigator.getInstance().gotoActivityHome(activity);
    }

    /////
    //// ACTIVE BATCH UPDATED events
    ////

    /// step started
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedStepStartedEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedStepStartedEvent");
        Timber.tag(TAG).d("   actorType        -> " + event.getActorType().toString());
        Timber.tag(TAG).d("   actionType       -> " + event.getActionType().toString());
        Timber.tag(TAG).d("   batchStarted     -> " + event.isBatchStarted());
        Timber.tag(TAG).d("   orderStarted     -> " + event.isOrderStarted());
        Timber.tag(TAG).d("   batchGuid        -> " + event.getBatchGuid());
        Timber.tag(TAG).d("   serviceOrderGuid -> " + event.getServiceOrderGuid());
        Timber.tag(TAG).d("   stepGuid         -> " + event.getStepGuid());
        Timber.tag(TAG).d("   taskType         -> " + event.getTaskType().toString());

        EventBus.getDefault().removeStickyEvent(event);

        ActivityNavigator.getInstance().gotoActiveBatchStep(activity, event.getActorType(), event.getActionType(), event.isBatchStarted(), event.isOrderStarted(), event.getBatchGuid(), event.getServiceOrderGuid(), event.getStepGuid(), event.getTaskType());

    }

    /// batch finished
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedBatchFinishedEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedBatchFinishedEvent");
        Timber.tag(TAG).d("   actorType        -> " + event.getActorType().toString());
        Timber.tag(TAG).d("   batchGuid        -> " + event.getBatchGuid());

        EventBus.getDefault().removeStickyEvent(event);
        ActivityNavigator.getInstance().gotoActivityHomeAndShowBatchFinishedMessage(activity, event.getActorType(), event.getBatchGuid());

    }

    /// batch waiting to finish
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedBatchWaitingToFinishEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedBatchWaitingToFinishEvent");
        Timber.tag(TAG).d("   actorType        -> " + event.getActorType().toString());
        Timber.tag(TAG).d("   batchGuid        -> " + event.getBatchGuid());

        EventBus.getDefault().removeStickyEvent(ActiveBatchUpdatedBatchWaitingToFinishEvent.class);
        ActivityNavigator.getInstance().gotoWaitingToFinishBatch(activity, event.getActorType(), event.getBatchGuid());

    }

    /// batch removed
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedBatchRemovedEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedBatchWaitingToFinishEvent");
        Timber.tag(TAG).d("   actorType        -> " + event.getActorType().toString());
        Timber.tag(TAG).d("   batchGuid        -> " + event.getBatchGuid());

        EventBus.getDefault().removeStickyEvent(event);
        ActivityNavigator.getInstance().gotoActivityHomeAndShowBatchRemovedMessage(activity, event.getActorType(), event.getBatchGuid());

    }

    /// no batch
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedNoBatchEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedNoBatchEvent");
        EventBus.getDefault().removeStickyEvent(event);
        ActivityNavigator.getInstance().gotoActivityHome(activity);

    }

}
