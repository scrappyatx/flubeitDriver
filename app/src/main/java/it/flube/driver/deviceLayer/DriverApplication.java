/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer;

import android.support.multidex.MultiDexApplication;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 6/28/2017
 * Project : Driver
 */

public class DriverApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        setupLocalDeviceLogging();
        setupMemoryLeakDetectionAndThreadAndVmPolicies();
        setupEventBus();
    }


    private void setupLocalDeviceLogging(){
        AndroidDevice.getInstance().setApplicationContext(getApplicationContext());
        AndroidDevice.getInstance().getAppLogging().initializeDeviceLogging();
    }

    private void setupMemoryLeakDetectionAndThreadAndVmPolicies(){
        AppInitialization appInitialization = new AppInitialization(getApplicationContext());

        appInitialization.initializeLeakDetection(this, getApplicationContext());
        appInitialization.setThreadPolicy();
        appInitialization.setVMPolicy();
        appInitialization.initializeAndCreateImageLoaderLogic();
        //appInitialization.initializeBugReporting(this);
    }

    private void setupEventBus(){
        EventBus.builder()
                .throwSubscriberException(false)
                .installDefaultEventBus();
    }

    public MobileDeviceInterface getMobileDevice() {
        return AndroidDevice.getInstance();
    }

}
