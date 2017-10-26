/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.applicationLayer;

import android.support.multidex.MultiDexApplication;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mapbox.mapboxsdk.Mapbox;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 6/28/2017
 * Project : Driver
 */

public class DriverApplication extends MultiDexApplication {
    private static final String TAG = "DriverApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        setupLocalDeviceLogging();
        setupMemoryLeakDetectionAndThreadAndVmPolicies();
        setupEventBus();
        setupMapBox();
        setupIconify();
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
        appInitialization.initializeBugReporting(this);
    }

    private void setupEventBus(){
        EventBus.builder()
                .throwSubscriberException(false)
                .installDefaultEventBus();
    }

    private void setupMapBox(){
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
    }

    private void setupIconify(){
        Iconify.with(new FontAwesomeModule());
    }

    public MobileDeviceInterface getMobileDevice() {
        return AndroidDevice.getInstance();
    }

}
