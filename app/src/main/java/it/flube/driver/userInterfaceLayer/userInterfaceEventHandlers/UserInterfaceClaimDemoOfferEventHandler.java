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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferSuccessAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferFailureEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferSuccessEvent;
import timber.log.Timber;

/**
 * Created on 12/17/2017
 * Project : Driver
 */

public class UserInterfaceClaimDemoOfferEventHandler {
    public final static String TAG = "UserInterfaceClaimDemoOfferEventHandler";

    private AppCompatActivity activity;

    public UserInterfaceClaimDemoOfferEventHandler(@NonNull AppCompatActivity activity){
        this.activity = activity;
        EventBus.getDefault().register(this);

        Timber.tag(TAG).d("created");
    }

    public void close(){
        EventBus.getDefault().unregister(this);
        activity = null;
        Timber.tag(TAG).d("closed");
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimDemoOfferSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimDemoOfferSuccessEvent.class);

        Timber.tag(TAG).d("claim demo offer SUCCESS!");

        EventBus.getDefault().postSticky(new ShowClaimOfferSuccessAlertEvent());
        ActivityNavigator.getInstance().gotoActivityDemoOffers(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimDemoOfferFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimDemoOfferFailureEvent.class);

        Timber.tag(TAG).d("claim demo offer FAILURE!");


        EventBus.getDefault().postSticky(new ShowClaimOfferFailureAlertEvent());
        ActivityNavigator.getInstance().gotoActivityDemoOffers(activity);
    }




}
