/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;

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
