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

public class DriverApplication extends MultiDexApplication implements
        ActivityLifecycleHandler.LifecycleListener {

    private static final String TAG = "DriverApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        setupLocalDeviceLogging();
        setupMemoryLeakDetectionAndThreadAndVmPolicies();
        setupEventBus();
        setupMapBox();
        setupIconify();

        // Register a lifecycle handler to track the foreground/background state
        registerActivityLifecycleCallbacks(new ActivityLifecycleHandler(this));
    }

    /**
     * Called right before the application is stopped.
     */
    public void onApplicationStopped(){
        Timber.tag(TAG).d("onApplicationStopped");
    }

    /**
     * Called right after the application has been started.
     */
    public void onApplicationStarted(){
        Timber.tag(TAG).d("onApplicationStarted");
    }

    /**
     * Called when the application is paused (but still awake).
     */
    public void onApplicationPaused(){
        Timber.tag(TAG).d("onApplicationPaused");
    }

    /**
     * Called right after the application has been resumed (come to the foreground).
     */
    public void onApplicationResumed(){
        Timber.tag(TAG).d("onApplicationResumed");
    }


    private void setupLocalDeviceLogging(){
        AndroidDevice.getInstance().setApplicationContext(getApplicationContext());
        AndroidDevice.getInstance().getAppLogging().initializeDeviceLogging();
    }

    private void setupMemoryLeakDetectionAndThreadAndVmPolicies(){
        AppInitialization appInitialization = new AppInitialization(getApplicationContext());

        appInitialization.initializeBugReporting(this);
        appInitialization.initializeLeakDetection(this, getApplicationContext());
        appInitialization.setThreadPolicy();
        appInitialization.setVMPolicy();
        appInitialization.initializeAndCreateImageLoaderLogic();

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
