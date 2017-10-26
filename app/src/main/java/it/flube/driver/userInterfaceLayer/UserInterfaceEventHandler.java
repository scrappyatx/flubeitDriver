/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.materialdrawer.holder.StringHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ScheduledBatchesAvailableResponseHandler;
import it.flube.driver.dataLayer.userInterfaceEvents.batchAlerts.ShowForfeitBatchAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferSuccessAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferTimeoutAlertEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferFailureEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferSuccessEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferFailureEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferSuccessEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferTimeoutEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferFailureEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferSuccessEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferTimeoutEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.DemoOffersUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PersonalOffersUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.offerListUpdates.PublicOffersUpdatedEvent;
import it.flube.driver.dataLayer.userInterfaceEvents.scheduledBatch.BatchForfeitedEvent;
import it.flube.driver.userInterfaceLayer.ActivityNavigator;
import timber.log.Timber;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class UserInterfaceEventHandler {
    public final static String TAG = "UserInterfaceEventHandler";

    private AppCompatActivity activity;
    private ActivityNavigator navigator;

    public UserInterfaceEventHandler(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator ){
        this.activity = activity;
        this.navigator = navigator;
        EventBus.getDefault().register(this);
    }

    public void close(){
        EventBus.getDefault().unregister(this);
        navigator = null;
        activity = null;
    }


    //// CLAIM PERSONAL OFFER events

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPersonalOfferSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPersonalOfferSuccessEvent.class);

        Timber.tag(TAG).d("claim personal offer SUCCESS!");

        EventBus.getDefault().postSticky(new ShowClaimOfferSuccessAlertEvent());
        navigator.gotoActivityPersonalOffers(activity);
        //TODO this should goto personal offers
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPersonalOfferFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPersonalOfferFailureEvent.class);

        Timber.tag(TAG).d("claim personal offer FAILURE!");

        EventBus.getDefault().postSticky(new ShowClaimOfferFailureAlertEvent());
        navigator.gotoActivityPersonalOffers(activity);
        //TODO this should goto personal offers
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPersonalOfferTimeoutEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPersonalOfferTimeoutEvent.class);

        Timber.tag(TAG).d("claim offer TIMEOUT!");

        EventBus.getDefault().postSticky(new ShowClaimOfferTimeoutAlertEvent());
        navigator.gotoActivityPersonalOffers(activity);
        //TODO this should goto personal offers
    }

    /// CLAIM PUBLIC OFFER events
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPublicOfferSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPublicOfferSuccessEvent.class);

        Timber.tag(TAG).d("claim personal offer SUCCESS!");

        EventBus.getDefault().postSticky(new ShowClaimOfferSuccessAlertEvent());
        navigator.gotoActivityPublicOffers(activity);
        //TODO this should goto public offers
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPublicOfferFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPublicOfferFailureEvent.class);

        Timber.tag(TAG).d("claim personal offer FAILURE!");

        EventBus.getDefault().postSticky(new ShowClaimOfferFailureAlertEvent());
        navigator.gotoActivityPublicOffers(activity);
        //TODO this should goto public offers
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimPublicOfferTimeoutEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimPublicOfferTimeoutEvent.class);

        Timber.tag(TAG).d("claim offer TIMEOUT!");

        EventBus.getDefault().postSticky(new ShowClaimOfferTimeoutAlertEvent());
        navigator.gotoActivityPublicOffers(activity);
        //TODO this should goto  public offers
    }


    /// CLAIM DEMO OFFER events
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimDemoOfferSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimDemoOfferSuccessEvent.class);

        Timber.tag(TAG).d("claim demo offer SUCCESS!");

        EventBus.getDefault().postSticky(new ShowClaimOfferSuccessAlertEvent());
        navigator.gotoActivityDemoOffers(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimDemoOfferFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimDemoOfferFailureEvent.class);

        Timber.tag(TAG).d("claim demo offer FAILURE!");


        EventBus.getDefault().postSticky(new ShowClaimOfferFailureAlertEvent());
        navigator.gotoActivityDemoOffers(activity);
    }

    /// FORFEIT BATCH EVENT
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(BatchForfeitedEvent event) {
        EventBus.getDefault().removeStickyEvent(BatchForfeitedEvent.class);

        Timber.tag(TAG).d("batch forfeited!");

        EventBus.getDefault().postSticky(new ShowForfeitBatchAlertEvent());
        navigator.gotoActivityScheduledBatches(activity);

    }

}
