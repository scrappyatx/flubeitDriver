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

    private UserInterfaceAuthChangeEventHandler userInterfaceAuthChangeEventHandler;
    private UserInterfaceClaimPublicOfferEventHandler userInterfaceClaimPublicOfferEventHandler;
    private UserInterfaceClaimPersonalOfferEventHandler userInterfaceClaimPersonalOfferEventHandler;
    private UserInterfaceClaimDemoOfferEventHandler userInterfaceClaimDemoOfferEventHandler;
    private UserInterfaceActiveBatchEventHandler userInterfaceActiveBatchEventHandler;
    private UserInterfaceForfeitBatchEventHandler userInterfaceForfeitBatchEventHandler;


    public UserInterfaceEventHandler(@NonNull AppCompatActivity activity){

        userInterfaceAuthChangeEventHandler = new UserInterfaceAuthChangeEventHandler(activity);
        userInterfaceClaimPublicOfferEventHandler = new UserInterfaceClaimPublicOfferEventHandler(activity);
        userInterfaceClaimPersonalOfferEventHandler = new UserInterfaceClaimPersonalOfferEventHandler(activity);
        userInterfaceClaimDemoOfferEventHandler = new UserInterfaceClaimDemoOfferEventHandler(activity);
        userInterfaceActiveBatchEventHandler = new UserInterfaceActiveBatchEventHandler(activity);
        userInterfaceForfeitBatchEventHandler = new UserInterfaceForfeitBatchEventHandler(activity);


    }

    public void close(){
        userInterfaceAuthChangeEventHandler.close();
        userInterfaceClaimPublicOfferEventHandler.close();
        userInterfaceClaimPersonalOfferEventHandler.close();
        userInterfaceClaimDemoOfferEventHandler.close();
        userInterfaceActiveBatchEventHandler.close();
        userInterfaceForfeitBatchEventHandler.close();

    }

}
