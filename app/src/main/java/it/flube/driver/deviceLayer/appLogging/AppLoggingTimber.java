/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.appLogging;

import android.content.Context;

import it.flube.driver.BuildConfig;
import it.flube.driver.modelLayer.interfaces.AppLoggingInterface;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import timber.log.Timber;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class AppLoggingTimber implements AppLoggingInterface {
    private Context mContext;

    public AppLoggingTimber(Context context) {
        mContext=context;
    }

    public void initializeDeviceLogging() {

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Timber.i("Timber --> Planted DebugTree tree for DEBUG build");
        }
        Timber.d("Device Logging Initialized");
    }

    public void initializeRemoteLoggingAndCrashReportingRequest(AppRemoteConfigInterface appConfig, AppLoggingInterface.Response response){
        // get rollbar error reporting settings
        Boolean isLoggingRollbarDebug =  appConfig.getErrorReportingDebugActive();
        Boolean isLoggingRollbarRelease = appConfig.getErrorReportingReleaseActive();
        Timber.d("isLoggingRollbarDebug --> " + isLoggingRollbarDebug);
        Timber.d("isLoggingRollbarRelease --> " + isLoggingRollbarRelease);

        //get loggly logging settings
        Boolean isLoggingLogglyDebug =  appConfig.getLoggingDebugActive();
        Boolean isLoggingLogglyRelease =  appConfig.getLoggingReleaseActive();
        Timber.d("isLoggingLogglyDebug --> " + isLoggingLogglyDebug);
        Timber.d("isLoggingLogglyRelease --> " + isLoggingLogglyRelease);

        if (BuildConfig.DEBUG) {
            if (isLoggingRollbarDebug) {
                Timber.plant(new RollbarCrashReporting(mContext));
                Timber.i("Timber --> Planted ROLLBAR tree for DEBUG build");

            }

            if (isLoggingLogglyDebug) {
                Timber.plant(new LogglyRemoteLogging(mContext));
                Timber.i("Timber --> Planted LOGGLY tree for DEBUG build");
            }
        } else {
            if (isLoggingRollbarRelease) {
                Timber.plant(new RollbarCrashReporting(mContext));
                Timber.i("Timber --> Planted ROLLBAR tree for RELEASE build");
            }

            if (isLoggingLogglyRelease) {
                Timber.plant(new LogglyRemoteLogging(mContext));
                Timber.i("Timber --> Planted LOGGLY tree for RELEASE build");
            }
        }
        Timber.d("Remote Logging and Crash Reporting Initialized");
        response.initializeRemoteLoggingAndCrashReportingComplete();
    }
}
