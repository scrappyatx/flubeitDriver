/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.appLogging;

import android.content.Context;

import it.flube.driver.BuildConfig;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import timber.log.Timber;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class AppLoggingTimber {

    private static final String TAG = "AppLoggingTimber";

    private Context appContext;


    public AppLoggingTimber(Context appContext) {
        this.appContext = appContext;
        initializeDeviceLogging();
        Timber.tag(TAG).d("created");
    }

    private void initializeDeviceLogging() {
        Timber.tag(TAG).d("initializeDeviceLogging STARTED...");
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Timber.tag(TAG).d("   ...planted DebugTree tree for DEBUG build");
        }
        Timber.tag(TAG).d("...COMPLETE");
    }

    public void initializeRemoteLoggingAndCrashReporting(CloudConfigInterface cloudConfig, DeviceInfo deviceInfo){
        Timber.tag(TAG).d("initializeRemoteLoggingAndCrashReporting STARTED...");

        // get rollbar error reporting settings
        Boolean isLoggingRollbarDebug =  cloudConfig.getErrorReportingDebugActive();
        Boolean isLoggingRollbarRelease = cloudConfig.getErrorReportingReleaseActive();
        Timber.tag(TAG).d("   ...isLoggingRollbarDebug --> " + isLoggingRollbarDebug);
        Timber.tag(TAG).d("   ...isLoggingRollbarRelease --> " + isLoggingRollbarRelease);

        //get loggly logging settings
        Boolean isLoggingLogglyDebug =  cloudConfig.getLoggingDebugActive();
        Boolean isLoggingLogglyRelease =  cloudConfig.getLoggingReleaseActive();
        Timber.tag(TAG).d("   ...isLoggingLogglyDebug --> " + isLoggingLogglyDebug);
        Timber.tag(TAG).d("   ...isLoggingLogglyRelease --> " + isLoggingLogglyRelease);

        if (BuildConfig.DEBUG) {
            if (isLoggingRollbarDebug) {
                Timber.plant(new RollbarCrashReporting(appContext, deviceInfo));
                Timber.tag(TAG).i("   ...planted ROLLBAR tree for DEBUG build");

            }

            if (isLoggingLogglyDebug) {
                Timber.plant(new LogglyRemoteLogging(appContext, deviceInfo));
                Timber.tag(TAG).i("   ...planted LOGGLY tree for DEBUG build");
            }
        } else {
            if (isLoggingRollbarRelease) {
                Timber.plant(new RollbarCrashReporting(appContext, deviceInfo));
                Timber.tag(TAG).i("   ...planted ROLLBAR tree for RELEASE build");
            }

            if (isLoggingLogglyRelease) {
                Timber.plant(new LogglyRemoteLogging(appContext, deviceInfo));
                Timber.tag(TAG).i("   ...planted LOGGLY tree for RELEASE build");
            }
        }
        Timber.tag(TAG).d("...COMPLETE");
    }
}
