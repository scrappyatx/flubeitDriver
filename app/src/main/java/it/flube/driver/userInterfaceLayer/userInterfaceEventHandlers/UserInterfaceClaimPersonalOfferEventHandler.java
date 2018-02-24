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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferTimeoutAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferFailureEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferSuccessEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferTimeoutEvent;
import timber.log.Timber;

/**
 * Created on 12/17/2017
 * Project : Driver
 */

public class UserInterfaceClaimPersonalOfferEventHandler {
    public final static String TAG = "UserInterfaceClaimPersonalOfferEventHandler";

    private AppCompatActivity activity;
    private ActivityNavigator navigator;

    public UserInterfaceClaimPersonalOfferEventHandler(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator){
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
    public void onEvent(ClaimPersonalOfferSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPersonalOfferSuccessEvent.class);

        Timber.tag(TAG).d("claim personal offer SUCCESS!");

        EventBus.getDefault().postSticky(new ShowClaimOfferSuccessAlertEvent());
        navigator.gotoActivityPersonalOffers(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPersonalOfferFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPersonalOfferFailureEvent.class);

        Timber.tag(TAG).d("claim personal offer FAILURE!");

        EventBus.getDefault().postSticky(new ShowClaimOfferFailureAlertEvent());
        navigator.gotoActivityPersonalOffers(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPersonalOfferTimeoutEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPersonalOfferTimeoutEvent.class);

        Timber.tag(TAG).d("claim personal offer TIMEOUT!");

        EventBus.getDefault().postSticky(new ShowClaimOfferTimeoutAlertEvent());
        navigator.gotoActivityPersonalOffers(activity);
    }
}
