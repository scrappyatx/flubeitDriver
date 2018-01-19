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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferSuccessAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferTimeoutAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferFailureEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferSuccessEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferTimeoutEvent;
import timber.log.Timber;

/**
 * Created on 12/17/2017
 * Project : Driver
 */

public class UserInterfaceClaimPublicOfferEventHandler {
    public final static String TAG = "UserInterfaceClaimPublicOfferEventHandler";

    private AppCompatActivity activity;
    private ActivityNavigator navigator;

    public UserInterfaceClaimPublicOfferEventHandler(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator){
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
    public void onEvent(ClaimPublicOfferSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPublicOfferSuccessEvent.class);

        Timber.tag(TAG).d("claim public offer SUCCESS!");

        EventBus.getDefault().postSticky(new ShowClaimOfferSuccessAlertEvent());
        navigator.gotoActivityPublicOffers(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPublicOfferFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPublicOfferFailureEvent.class);

        Timber.tag(TAG).d("claim public offer FAILURE!");

        EventBus.getDefault().postSticky(new ShowClaimOfferFailureAlertEvent());
        navigator.gotoActivityPublicOffers(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPublicOfferTimeoutEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPublicOfferTimeoutEvent.class);

        Timber.tag(TAG).d("claim public offer TIMEOUT!");

        EventBus.getDefault().postSticky(new ShowClaimOfferTimeoutAlertEvent());
        navigator.gotoActivityPublicOffers(activity);
    }

}
