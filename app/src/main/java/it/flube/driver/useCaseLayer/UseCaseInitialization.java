/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer;

import android.os.Handler;
import android.os.Looper;

import it.flube.driver.useCaseLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.useCaseLayer.interfaces.AppLoggingInterface;
import it.flube.driver.useCaseLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public class UseCaseInitialization implements Runnable, AppRemoteConfigInterface.Response, AppLoggingInterface.Response {
    private Response mResponse;
    private MobileDeviceInterface mDevice;

    public UseCaseInitialization(MobileDeviceInterface device, Response response) {
        mResponse = response;
        mDevice = device;
    }

    public void run() {
        // Step 1 --> initialize app config
        mDevice.getAppRemoteConfig().getUpdatedValuesFromRemoteServerRequest(this);
    }

    public void getUpdatedValuesFromRemoteServerComplete(){
        // Step 2 --> initialize local logging
        mDevice.getAppLogging().initializeRemoteLoggingAndCrashReportingRequest(mDevice.getAppRemoteConfig(), this);
    }

    public void initializeRemoteLoggingAndCrashReportingComplete(){
        mResponse.useCaseInitializationComplete();
    }

    public interface Response {
        void useCaseInitializationComplete();
    }
}
