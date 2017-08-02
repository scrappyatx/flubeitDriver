/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer;

import android.content.Context;

import it.flube.driver.deviceLayer.AblyConnectionWrapper;
import it.flube.driver.deviceLayer.AppRemoteConfig;
import it.flube.driver.deviceLayer.AppUser;
import it.flube.driver.deviceLayer.CloudDatabaseFirebase;
import it.flube.driver.deviceLayer.DeviceStorageSharedPrefs;
import it.flube.driver.deviceLayer.LocationEngineWrapper;
import it.flube.driver.deviceLayer.UserProfile;
import it.flube.driver.deviceLayer.appLogging.AppLoggingTimber;
import it.flube.driver.deviceLayer.cloudAuth.CloudAuthFirebase;
import it.flube.driver.deviceLayer.realtimeMessaging.RealtimeBatchMessages;
import it.flube.driver.deviceLayer.realtimeMessaging.RealtimeOfferMessages;
import it.flube.driver.modelLayer.interfaces.AppLoggingInterface;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.modelLayer.interfaces.AppUserInterface;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import it.flube.driver.modelLayer.interfaces.UserProfileInterface;

/**
 * Created on 7/3/2017
 * Project : Driver
 */

public class AndroidDevice implements MobileDeviceInterface {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile AndroidDevice mInstance = new AndroidDevice();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private AndroidDevice() {}

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static AndroidDevice getInstance() {
        return AndroidDevice.Loader.mInstance;
    }

    private static final String TAG = "AndroidDevice";
    private Context applicationContext;

    private AppLoggingInterface logging;
    private DeviceStorageInterface localStorage;
    private UserProfileInterface userProfile;
    private LocationTelemetryInterface locationTelemetry;


    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Context getApplicationContext(){
        return applicationContext;
    }

    public AppLoggingInterface getAppLogging(){
        if (logging == null) {
            logging = new AppLoggingTimber(applicationContext);
        }
        return logging;
    }

    public AppRemoteConfigInterface getAppRemoteConfig() {
        return AppRemoteConfig.getInstance();
    }

    public CloudAuthInterface getCloudAuth() {
        return CloudAuthFirebase.getInstance();
    }

    public CloudDatabaseInterface getCloudDatabase(){
        return CloudDatabaseFirebase.getInstance();
    }

    public CloudStorageInterface getCloudStorage() {
        return null;
    }

    public DeviceStorageInterface getDeviceStorage() {
        if (localStorage == null) {
            localStorage = new DeviceStorageSharedPrefs(applicationContext);
        }
        return localStorage;
    }

    public UserProfileInterface getUserProfile() {
        if (userProfile == null) {
            userProfile = new UserProfile();
        }
        return userProfile;
    }

    public RealtimeMessagingInterface.Connection getRealtimeConnection() {
        return AblyConnectionWrapper.getInstance();
    }

    public RealtimeMessagingInterface.OfferChannel getRealtimeOfferMessages() {
        return RealtimeOfferMessages.getInstance();
    }

    public RealtimeMessagingInterface.BatchChannel getRealtimeBatchMessages() {
        return RealtimeBatchMessages.getInstance();
    }

    public AppUserInterface getUser() {
        return AppUser.getInstance();
    }

    public LocationTelemetryInterface getLocationTelemetry() {
        if (locationTelemetry == null) {
            locationTelemetry = new LocationEngineWrapper(applicationContext);
        }
        return locationTelemetry;
    }


}
