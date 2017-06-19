/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCases.initializeRemoteLoggingAndErrorReporting;

import android.content.Context;


import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import it.flube.driver.BuildConfig;
import it.flube.driver.R;
import it.flube.driver.dataLayer.remoteLoggingAndErrorReporting.LogglyRemoteLogging;
import it.flube.driver.dataLayer.remoteLoggingAndErrorReporting.RollbarCrashReporting;
import timber.log.Timber;

/**
 * Created on 6/16/2017
 * Project : Driver
 */

public class UseCaseInitRemoteLoggingAndErrorReporting implements Runnable {
    private static String mLOGGLY_DEBUG_ACTIVE_KEY;
    private static String mLOGGLY_RELEASE_ACTIVE_KEY ;
    private static String mROLLBAR_DEBUG_ACTIVE_KEY;
    private static String mROLLBAR_RELEASE_ACTIVE_KEY;

    private Context mContext;
    private UseCaseInitRemoteLoggingAndErrorReportingCallback mCallback;

    public UseCaseInitRemoteLoggingAndErrorReporting(Context context, UseCaseInitRemoteLoggingAndErrorReportingCallback callback) {
        mContext = context;
        mCallback = callback;

        mLOGGLY_DEBUG_ACTIVE_KEY = mContext.getResources().getString(R.string.LOGGLY_DEBUG_ACTIVE_KEY);
        mLOGGLY_RELEASE_ACTIVE_KEY = mContext.getResources().getString(R.string.LOGGLY_RELEASE_ACTIVE_KEY);
        mROLLBAR_DEBUG_ACTIVE_KEY = mContext.getResources().getString(R.string.ROLLBAR_DEBUG_ACTIVE_KEY);
        mROLLBAR_RELEASE_ACTIVE_KEY = mContext.getResources().getString(R.string.ROLLBAR_RELEASE_ACTIVE_KEY);

    }

    public void run() {

        // get rollbar error reporting settings
        Boolean isLoggingRollbarDebug =  FirebaseRemoteConfig.getInstance().getBoolean(mROLLBAR_DEBUG_ACTIVE_KEY);
        Boolean isLoggingRollbarRelease = FirebaseRemoteConfig.getInstance().getBoolean(mROLLBAR_RELEASE_ACTIVE_KEY);
        Timber.d("isLoggingRollbarDebug --> " + isLoggingRollbarDebug);
        Timber.d("isLoggingRollbarRelease --> " + isLoggingRollbarRelease);

        //get loggly logging settings
        Boolean isLoggingLogglyDebug =  FirebaseRemoteConfig.getInstance().getBoolean(mLOGGLY_DEBUG_ACTIVE_KEY);
        Boolean isLoggingLogglyRelease =  FirebaseRemoteConfig.getInstance().getBoolean(mLOGGLY_RELEASE_ACTIVE_KEY);
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
        Timber.d("*** UseCase complete --> Remote Logging and Error Reporting Initialized");
        mCallback.UseCaseInitRemoteLoggingAndErrorReportingComplete(true, "Success");
    }

}
