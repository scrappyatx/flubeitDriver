/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import timber.log.Timber;

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
        userInterfaceForfeitBatchEventHandler = new UserInterfaceForfeitBatchEventHandler(activity);
        Timber.tag(TAG).d("created");
    }

    public void startMonitoringActiveBatch(@NonNull AppCompatActivity activity){
        Timber.tag(TAG).d("startMonitoringActiveBatch");
        userInterfaceActiveBatchEventHandler = new UserInterfaceActiveBatchEventHandler(activity);
    }


    public void close(){
        Timber.tag(TAG).d("close");
        userInterfaceAuthChangeEventHandler.close();
        userInterfaceClaimPublicOfferEventHandler.close();
        userInterfaceClaimPersonalOfferEventHandler.close();
        userInterfaceClaimDemoOfferEventHandler.close();
        userInterfaceForfeitBatchEventHandler.close();

        if (userInterfaceActiveBatchEventHandler != null){
            userInterfaceActiveBatchEventHandler.close();
        }

    }

}
