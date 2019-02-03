/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.applicationLayer;

import android.content.res.Configuration;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.mapbox.mapboxsdk.Mapbox;


import io.fabric.sdk.android.Fabric;
import org.greenrobot.eventbus.EventBus;

import it.flube.driver.R;
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
import it.flube.libbatchdata.builders.BuilderUtilities;
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
    private String appGuid;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Answers(), new Crashlytics());
        appGuid = BuilderUtilities.generateGuid();
        Timber.tag(TAG).d("onCreate (%s)", appGuid);

        //initialize our device singleton
        AndroidDevice.getInstance().initialize(getApplicationContext());


        //now do setup work
        setupMemoryLeakDetectionAndThreadAndVmPolicies();
        setupEventBus();
        setupMapBox();
        setupIconify();

        new NotificationChannelSetup().createNotificationChannel(this);

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

        Timber.tag(TAG).d("onConfigurationChanged (%s)", appGuid);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Timber.tag(TAG).d("onLowMemory (%s)", appGuid);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Timber.tag(TAG).d("onLowMemory (%s) level -> %s", appGuid, level);
    }




    /**
     * Called right before the application is stopped.
     */
    public void onApplicationStopped(){
        Timber.tag(TAG).d("onApplicationStopped (%s)", appGuid);
    }

    /**
     * Called right after the application has been started.
     */
    public void onApplicationStarted(){
        Timber.tag(TAG).d("onApplicationStarted (%s)", appGuid);
    }

    /**
     * Called when the application is paused (but still awake).
     */
    public void onApplicationPaused(){
        Timber.tag(TAG).d("onApplicationPaused (%s)", appGuid);
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenApplicationPauses(device));
    }

    /**
     * Called right after the application has been resumed (come to the foreground).
     */
    public void onApplicationResumed(){
        Timber.tag(TAG).d("onApplicationResumed (%s)", appGuid);
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenApplicationResumes(device));
    }

    private void setupMemoryLeakDetectionAndThreadAndVmPolicies(){
        AppInitialization appInitialization = new AppInitialization(getApplicationContext());

        appInitialization.initializeBugReporting(this);
        //appInitialization.initializeLeakDetection(this, getApplicationContext());
        appInitialization.setThreadPolicy();
        appInitialization.setVMPolicy();
        appInitialization.initializeAndCreateImageLoaderLogic();
        Timber.tag(TAG).d("setupMemoryLeakDetectionAndThreadAndVmPolicies (%s)", appGuid);
    }

    private void setupEventBus(){
        EventBus.builder()
                .throwSubscriberException(false)
                .installDefaultEventBus();
        Timber.tag(TAG).d("setupEventBus (%s)", appGuid);
    }

    private void setupMapBox(){
        //Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        Mapbox.getInstance(this.getApplicationContext(), getString(R.string.mapbox_access_token));
        Timber.tag(TAG).d("setupMapBox (%s)", appGuid);
    }

    private void setupIconify(){
        Iconify.with(new FontAwesomeModule());
        Timber.tag(TAG).d("setupIconify (%s)", appGuid);
    }


}
