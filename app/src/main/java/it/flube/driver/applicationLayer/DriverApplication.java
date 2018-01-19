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
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.modelLayer.interfaces.AppUserInterface;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
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
    private CloudAuthInterface cloudAuth;
    private CloudDatabaseInterface cloudDb;
    private LocationTelemetryInterface locationTelemetry;
    private AppRemoteConfigInterface remoteConfig;
    private UseCaseInterface useCaseEngine;

    private AppUserInterface appUser;
    private ActiveBatchInterface activeBatch;
    private OffersInterface offerLists;

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

        // set references to device & to cloud auth
        device = AndroidDevice.getInstance();
        cloudAuth = device.getCloudAuth();
        cloudDb = device.getCloudDatabase();
        appUser = device.getUser();

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
        setReferencesToSingletons();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenApplicationPauses(device));
    }

    /**
     * Called right after the application has been resumed (come to the foreground).
     */
    public void onApplicationResumed(){
        Timber.tag(TAG).d("onApplicationResumed");
        releaseReferencesToSingletons();
        device.getUseCaseEngine().getUseCaseExecutor().execute(new UseCaseThingsToDoWhenApplicationResumes(device));
    }


    private void setupLocalDeviceLogging(){
        AndroidDevice.getInstance().setApplicationContext(getApplicationContext());
        AndroidDevice.getInstance().getAppLogging().initializeDeviceLogging();
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

    private void setReferencesToSingletons(){
       locationTelemetry = device.getLocationTelemetry();
       remoteConfig = device.getAppRemoteConfig();
       useCaseEngine = device.getUseCaseEngine();

       activeBatch = device.getActiveBatch();
       offerLists = device.getOfferLists();

       Timber.tag(TAG).d("set references to singletons");
    }

    private void releaseReferencesToSingletons(){
        locationTelemetry = null;
        remoteConfig = null;
        useCaseEngine = null;

        activeBatch = null;
        offerLists = null;

        Timber.tag(TAG).d("released references to singletons");

    }

    public MobileDeviceInterface getMobileDevice() {
        return AndroidDevice.getInstance();
    }

}
