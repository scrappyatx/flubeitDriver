/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public interface AppLoggingInterface {

    void initializeDeviceLogging();

    void initializeRemoteLoggingAndCrashReportingRequest(AppRemoteConfigInterface appConfig, AppLoggingInterface.Response response);

    interface Response {

        void initializeRemoteLoggingAndCrashReportingComplete();
    }
}
