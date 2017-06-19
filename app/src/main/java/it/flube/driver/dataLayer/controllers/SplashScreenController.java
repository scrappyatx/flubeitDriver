/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.controllers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import it.flube.driver.dataLayer.messaging.RemoteServerMessaging;
import it.flube.driver.dataLayer.useCases.initializeDeviceLogging.UseCaseInitDeviceLogging;
import it.flube.driver.dataLayer.useCases.initializeDeviceLogging.UseCaseInitDeviceLoggingCallback;
import it.flube.driver.dataLayer.useCases.initializeRemoteConfig.UseCaseInitFirebaseRemoteConfig;
import it.flube.driver.dataLayer.useCases.initializeRemoteConfig.UseCaseInitFirebaseRemoteConfigCallback;
import it.flube.driver.dataLayer.useCases.initializeRemoteLoggingAndErrorReporting.UseCaseInitRemoteLoggingAndErrorReporting;
import it.flube.driver.dataLayer.useCases.initializeRemoteLoggingAndErrorReporting.UseCaseInitRemoteLoggingAndErrorReportingCallback;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.useCases.driver.signIn.UseCaseSignInFromDeviceStorage;
import it.flube.driver.modelLayer.useCases.driver.signIn.UseCaseSignInFromDeviceStorageCallback;
import it.flube.driver.userInterfaceLayer.activityNavigation.ActivityNavigatorSingleton;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoHomeActivityEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoLoginActivityEvent;
import it.flube.driver.dataLayer.storage.DriverStorage;
import timber.log.Timber;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bryan on 4/30/2017.
 */

public class SplashScreenController implements UseCaseInitDeviceLoggingCallback, UseCaseInitFirebaseRemoteConfigCallback,
        UseCaseInitRemoteLoggingAndErrorReportingCallback, UseCaseSignInFromDeviceStorageCallback {

    private final String TAG = "SplashScreenController";
    private Context mContext;
    private ExecutorService mUseCaseExec;

    //controller constructor
    public SplashScreenController(Context context) {
        mContext = context;
        mUseCaseExec = Executors.newSingleThreadExecutor();
    }

    public void startupSequence() {
        // startup sequence is
        // 1. setup logging on local device
        // 2. setup remote logging & error reporting
        // 3. get current remote config values from firebase
        // 4. attempt to sign in from the local device

        ///
        ///  **** STEP 1 - Setup Logging on Local Device ***
        ///
        mUseCaseExec.execute(new UseCaseInitDeviceLogging(this));
    }

    ///
    /// Step 1 Use Case Callback --> UseCaseInitDeviceLoggingCallback
    ///
    public void UseCaseInitDeviceLoggingComplete(Boolean resultSuccess, String resultMessage) {
        Timber.tag(TAG).d("UseCaseInitDeviceLogging COMPLETED ---> resultSuccess --> " + resultSuccess + " resultMessage ----> " + resultMessage);
        ///
        ///  **** STEP 2 - Setup Remote Logging & Error Reporting ***
        ///
        mUseCaseExec.execute(new UseCaseInitRemoteLoggingAndErrorReporting(mContext, this));
    }

    ///
    ///  Step 2 Use Case Callback --> UseCaseInitRemoteLoggingAndErrorReportingCallback
    ///

    public void UseCaseInitRemoteLoggingAndErrorReportingComplete(Boolean resultSuccess, String resultMessage) {
        //try to load the driver from device storage
        //Timber.tag(TAG).d("starting UseCaseSignInFromDeviceStorage");
        //Thread t = new Thread(new UseCaseSignInFromDeviceStorage(new DriverStorage(mContext), this));
        //t.start();
        Timber.tag(TAG).d("UseCaseInitRemoteLoggingAndErrorReporting COMPLETED ---> resultSuccess --> " + resultSuccess + " resultMessage ----> " + resultMessage);
        ///
        ///  **** STEP 3 - Get Current RemoteConfig values from Firebase ***
        ///
        ///
        mUseCaseExec.execute(new UseCaseInitFirebaseRemoteConfig(this));
    }

    ///
    ///  Step 3 Use Case Callbacks --> UseCaseInitFirebaseRemoteConfigCallback
    ///

    public void UseCaseInitFirebaseRemoteConfigComplete(Boolean resultSuccess, String resultMessage) {
        Timber.tag(TAG).d("UseCaseInitFirebaseRemoteConfig COMPLETED ---> resultSuccess --> " + resultSuccess + " resultMessage ----> " + resultMessage);
        ///
        ///  **** STEP 4 - Attempt to Sign In from the local device ***
        ///
        mUseCaseExec.execute(new UseCaseSignInFromDeviceStorage(new DriverStorage(mContext), this));
    }

    ///
    /// Step 4 Use Case Callback --> UseCaseSignInFromDeviceStorageCallback
    ///
    public void SignInFromDeviceStorageSuccess() {
        Timber.tag(TAG).d("UseCaseSignInFromDeviceStorge COMPLETED ---> SUCCESS");
        Timber.tag(TAG).i("*** Sign In SUCCESS -> Driver data loaded from device");

        mUseCaseExec.shutdown();

        String serverUrl = "https://api.cloudconfidant.com/concierge-oil-service/ably/token";
        String clientId = DriverSingleton.getInstance().getClientId();
        String lookingForOffersChannelName = "LookingForOffers";
        String batchActivityChannelName = "BatchActivity";

        //RemoteServerMessaging.getInstance().setConnectionValues(serverUrl,clientId,lookingForOffersChannelName,batchActivityChannelName);

        //EventBus.getDefault().post(new GotoHomeActivityEvent());
        //Timber.tag(TAG).d("*** posted GotoHomeActivityEvent on EventBus");
        ActivityNavigatorSingleton.getInstance().gotoActivityHome(mContext);
    }

    public void SignInFromDeviceStorageFailure(String errorMessage) {
        Timber.tag(TAG).d("UseCaseSignInFromDeviceStorge COMPLETED ---> FAIL");
        Timber.tag(TAG).i("*** Driver data couldn't be loaded from device -> " + errorMessage);

        mUseCaseExec.shutdown();

        ActivityNavigatorSingleton.getInstance().gotoActivityLogin(mContext);
        // need to go to login activity
        //EventBus.getDefault().post(new GotoLoginActivityEvent());
        //Timber.tag(TAG).d("*** posted GotoLoginActivityEvent on EventBus");
    }

}