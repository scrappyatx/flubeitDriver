/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.useCases.initializeDeviceLogging;

import it.flube.driver.BuildConfig;
import timber.log.Timber;

/**
 * Created on 6/17/2017
 * Project : Driver
 */

public class UseCaseInitDeviceLogging implements Runnable {
    private UseCaseInitDeviceLoggingCallback mCallback;

    public UseCaseInitDeviceLogging(UseCaseInitDeviceLoggingCallback callback) {
        mCallback = callback;
    }

    public void run(){
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Timber.i("Timber --> Planted DebugTree tree for DEBUG build");
        }
        Timber.d("*** UseCase complete --> Device Logging Initialized");
        mCallback.UseCaseInitDeviceLoggingComplete(true, "Success");
    }

    public void perform() {

    }
}
