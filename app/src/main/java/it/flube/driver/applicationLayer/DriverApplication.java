/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.applicationLayer;

import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;


import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.driver.modelLayer.interfaces.UseCaseInterface;
import it.flube.driver.useCaseLayer.appLifecycle.UseCaseThingsToDoWhenApplicationPauses;
import it.flube.driver.useCaseLayer.appLifecycle.UseCaseThingsToDoWhenApplicationResumes;
import timber.log.Timber;

/**
 * Created on 6/28/2017
 * Project : Driver
 */

public class DriverApplication extends MultiDexApplication implements
        ActivityLifecycleHandler.LifecycleListener {

    private static final String TAG = "DriverApplication";

    //references to device layer objects
    //to keep them from being garbage collected
    private MobileDeviceInterface device;


    @Override
    public void onCreate() {
        super.onCreate();

        //initialize our device singleton
        AndroidDevice.getInstance().initialize(getApplicationContext());

        //now do setup work
        setupMemoryLeakDetectionAndThreadAndVmPolicies();
        setupEventBus();
        setupMapBox();
        setupIconify();

        // Register a lifecycle handler to track the foreground/background state
        registerActivityLifecycleCallbacks(new ActivityLifecycleHandler(this));

        // set references to device & to cloud auth
        device = AndroidDevice.getInstance();
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Timber.tag(TAG).d("onConfigurationChanged");
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.tag(TAG).d("onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Timber.tag(TAG).d("onTrimMemory, level -> " + level);
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
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenApplicationPauses(device));
    }

    /**
     * Called right after the application has been resumed (come to the foreground).
     */
    public void onApplicationResumed(){
        Timber.tag(TAG).d("onApplicationResumed");
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenApplicationResumes(device));
    }

    private void setupMemoryLeakDetectionAndThreadAndVmPolicies(){
        AppInitialization appInitialization = new AppInitialization(getApplicationContext());

        appInitialization.initializeBugReporting(this);
        //appInitialization.initializeLeakDetection(this, getApplicationContext());
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
        //Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        //Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));
    }

    private void setupIconify(){
        Iconify.with(new FontAwesomeModule());
    }


}
