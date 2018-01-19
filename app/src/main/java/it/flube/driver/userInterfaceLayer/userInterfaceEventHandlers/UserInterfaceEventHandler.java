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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.batchAlerts.ShowForfeitBatchAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthNoUserEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth.CloudAuthUserChangedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferFailureAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferSuccessAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerAlerts.ShowClaimOfferTimeoutAlertEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferFailureEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.demoOffers.ClaimDemoOfferSuccessEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferFailureEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferSuccessEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.personalOffers.ClaimPersonalOfferTimeoutEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferFailureEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferSuccessEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerClaim.publicOffers.ClaimPublicOfferTimeoutEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatch.BatchForfeitedEvent;
import timber.log.Timber;

/**
 * Created on 10/14/2017
 * Project : Driver
 */

public class UserInterfaceEventHandler {
    public final static String TAG = "UserInterfaceEventHandler";

    private AppCompatActivity activity;
    private ActivityNavigator navigator;

    private UserInterfaceAuthChangeEventHandler userInterfaceAuthChangeEventHandler;
    private UserInterfaceClaimPublicOfferEventHandler userInterfaceClaimPublicOfferEventHandler;
    private UserInterfaceClaimPersonalOfferEventHandler userInterfaceClaimPersonalOfferEventHandler;
    private UserInterfaceClaimDemoOfferEventHandler userInterfaceClaimDemoOfferEventHandler;
    private UserInterfaceActiveBatchEventHandler userInterfaceActiveBatchEventHandler;
    private UserInterfaceForfeitBatchEventHandler userInterfaceForfeitBatchEventHandler;


    public UserInterfaceEventHandler(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator ){
        this.activity = activity;
        this.navigator = navigator;

        userInterfaceAuthChangeEventHandler = new UserInterfaceAuthChangeEventHandler(activity, navigator);
        userInterfaceClaimPublicOfferEventHandler = new UserInterfaceClaimPublicOfferEventHandler(activity,navigator);
        userInterfaceClaimPersonalOfferEventHandler = new UserInterfaceClaimPersonalOfferEventHandler(activity,navigator);
        userInterfaceClaimDemoOfferEventHandler = new UserInterfaceClaimDemoOfferEventHandler(activity, navigator);
        userInterfaceActiveBatchEventHandler = new UserInterfaceActiveBatchEventHandler(activity, navigator);
        userInterfaceForfeitBatchEventHandler = new UserInterfaceForfeitBatchEventHandler(activity, navigator);


    }

    public void close(){
        userInterfaceAuthChangeEventHandler.close();
        userInterfaceClaimPublicOfferEventHandler.close();
        userInterfaceClaimPersonalOfferEventHandler.close();
        userInterfaceClaimDemoOfferEventHandler.close();
        userInterfaceActiveBatchEventHandler.close();
        userInterfaceForfeitBatchEventHandler.close();

        navigator = null;
        activity = null;
    }

}
