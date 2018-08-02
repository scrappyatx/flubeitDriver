/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer;

import android.content.Context;

import it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseWrapper;
import it.flube.driver.deviceLayer.cloudServices.cloudDemoOffer.DemoOfferFirebaseWrapper;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageFirebaseWrapper;
import it.flube.driver.deviceLayer.cloudServices.cloudOfferClaim.OfferClaimFirebaseWrapper;
import it.flube.driver.deviceLayer.cloudServices.cloudPersonalOffer.PersonalOfferFirebaseWrapper;
import it.flube.driver.deviceLayer.cloudServices.cloudPublicOffer.PublicOfferFirebaseWrapper;
import it.flube.driver.deviceLayer.cloudServices.cloudScheduledBatch.ScheduledBatchFirebaseWrapper;
import it.flube.driver.deviceLayer.cloudServices.cloudUserAndDeviceInfoStorage.firebaseFirestore.UserAndDeviceInfoStorageFirestoreWrapper;
import it.flube.driver.deviceLayer.cloudServices.cloudUserProfile.UserProfileFirebaseWrapper;
import it.flube.driver.deviceLayer.cloudServices.firebaseRealtimeDatabase.FirebaseRealtimeDatabaseWrapper;
import it.flube.driver.deviceLayer.deviceServices.deviceImageStorage.DeviceImageStorage;
import it.flube.driver.deviceLayer.deviceInfo.DeviceDetails;
import it.flube.driver.deviceLayer.deviceServices.googlePlayLocation.GooglePlayLocationWrapper;
import it.flube.driver.deviceLayer.deviceServices.activeBatchForegroundService.ActiveBatchForegroundServiceController;
import it.flube.driver.deviceLayer.appDataStructures.ActiveBatch;
import it.flube.driver.deviceLayer.cloudServices.cloudConfig.FirebaseRemoteConfigWrapper;
import it.flube.driver.deviceLayer.appDataStructures.AppUser;
import it.flube.driver.deviceLayer.appDataStructures.OfferLists;
import it.flube.driver.deviceLayer.deviceServices.useCaseEngine.UseCaseEngine;
import it.flube.driver.deviceLayer.cloudServices.cloudDatabase.CloudDatabaseFirebaseWrapper;
import it.flube.driver.deviceLayer.deviceServices.deviceSharedPrefs.DeviceStorageSharedPrefs;
import it.flube.driver.deviceLayer.deviceServices.appLogging.AppLoggingTimber;
import it.flube.driver.deviceLayer.cloudServices.cloudAuth.CloudAuthFirebaseWrapper;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudConfigInterface;
import it.flube.driver.modelLayer.interfaces.AppUserInterface;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudDemoOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.CloudOfferClaimInterface;
import it.flube.driver.modelLayer.interfaces.CloudPersonalOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudPublicOfferInterface;
import it.flube.driver.modelLayer.interfaces.CloudScheduledBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudUserAndDeviceInfoStorageInterface;
import it.flube.driver.modelLayer.interfaces.CloudUserProfileInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.driver.modelLayer.interfaces.UseCaseInterface;
import timber.log.Timber;

/**
 * Created on 7/3/2017
 * Project : Driver
 */

public class AndroidDevice implements
        MobileDeviceInterface,
        DeviceDetails.Response {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile AndroidDevice instance = new AndroidDevice();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private AndroidDevice() {}

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static AndroidDevice getInstance() {
        return AndroidDevice.Loader.instance;
    }

    private static final String TAG = "AndroidDevice";


    //// cloud services
    private CloudAuthFirebaseWrapper cloudAuth;
    private CloudDatabaseFirebaseWrapper cloudDatabase;
    private CloudImageStorageFirebaseWrapper cloudImageStorage;
    private FirebaseRemoteConfigWrapper cloudConfig;
    private UserProfileFirebaseWrapper cloudUserProfile;
    private UserAndDeviceInfoStorageFirestoreWrapper cloudUserAndDeviceInfoStorage;

    private DemoOfferFirebaseWrapper cloudDemoOffer;
    private PersonalOfferFirebaseWrapper cloudPersonalOffer;
    private PublicOfferFirebaseWrapper cloudPublicOffer;

    private ScheduledBatchFirebaseWrapper cloudScheduledBatch;

    private ActiveBatchFirebaseWrapper cloudActiveBatch;

    //// device services
    private ActiveBatchForegroundServiceInterface foregroundService;
    private AppLoggingTimber logging;
    private DeviceStorageInterface localStorage;
    private DeviceImageStorageInterface localImageStorage;
    private LocationTelemetryInterface locationTelemetry;
    private UseCaseEngine useCaseEngine;

    //// application data
    private AppUser appUser;
    private DeviceDetails deviceDetails;
    private ActiveBatch activeBatch;
    private OfferLists offerLists;


    public void initialize(Context applicationContext) {

        //setup logging
        logging = new AppLoggingTimber(applicationContext);

        //setup device services
        useCaseEngine = new UseCaseEngine();
        foregroundService = new ActiveBatchForegroundServiceController(applicationContext);
        localStorage = new DeviceStorageSharedPrefs(applicationContext);
        localImageStorage = new DeviceImageStorage(applicationContext);

        locationTelemetry=new GooglePlayLocationWrapper(applicationContext);

        //setup cloud services
        cloudConfig = new FirebaseRemoteConfigWrapper();
        cloudAuth = new CloudAuthFirebaseWrapper();

        //initialize firebase realtime database before using any classes that reference it
        FirebaseRealtimeDatabaseWrapper.initializeDb();

        cloudImageStorage = new CloudImageStorageFirebaseWrapper(cloudConfig);
        cloudUserProfile = new UserProfileFirebaseWrapper();
        cloudUserAndDeviceInfoStorage = new UserAndDeviceInfoStorageFirestoreWrapper();

        cloudDemoOffer = new DemoOfferFirebaseWrapper(cloudConfig);
        cloudPersonalOffer = new PersonalOfferFirebaseWrapper(cloudConfig);
        cloudPublicOffer = new PublicOfferFirebaseWrapper(cloudConfig);

        cloudScheduledBatch = new ScheduledBatchFirebaseWrapper(cloudConfig);

        cloudActiveBatch = new ActiveBatchFirebaseWrapper(cloudConfig);

        //initialize our user and device details
        appUser = new AppUser(applicationContext);
        deviceDetails = new DeviceDetails(applicationContext, this);

        // initialize app data structures
        offerLists = new OfferLists();
        activeBatch = new ActiveBatch();

        Timber.tag(TAG).d("initialized");
    }

    public void deviceDetailsUpdateComplete(){
        logging.initializeRemoteLoggingAndCrashReporting(cloudConfig, deviceDetails.getDeviceInfo());
    }

    ///
    /// cloud services
    ///

    public CloudConfigInterface getCloudConfig() {
        return cloudConfig;
    }

    public CloudAuthInterface getCloudAuth() {
        return cloudAuth;
    }

    public CloudImageStorageInterface getCloudImageStorage() { return cloudImageStorage; }

    public CloudUserProfileInterface getCloudUserProfile() { return cloudUserProfile; }

    public CloudUserAndDeviceInfoStorageInterface getCloudUserAndDeviceInfoStorage(){ return cloudUserAndDeviceInfoStorage; }


    public CloudDemoOfferInterface getCloudDemoOffer() {    return cloudDemoOffer; }

    public CloudPersonalOfferInterface getCloudPersonalOffer() { return cloudPersonalOffer; }

    public CloudPublicOfferInterface getCloudPublicOffer() { return cloudPublicOffer;}

    public CloudScheduledBatchInterface getCloudScheduledBatch() { return cloudScheduledBatch; }

    public CloudActiveBatchInterface getCloudActiveBatch() { return cloudActiveBatch; }

    ///
    /// device services
    ///

    public ActiveBatchForegroundServiceInterface getActiveBatchForegroundServiceController(){
        return foregroundService;
    }

    public DeviceStorageInterface getDeviceStorage() {
        return localStorage;
    }

    public DeviceImageStorageInterface getDeviceImageStorage(){
        return localImageStorage;
    }

    public LocationTelemetryInterface getLocationTelemetry() {
        return locationTelemetry;
    }

    public UseCaseInterface getUseCaseEngine(){
        return useCaseEngine;
    }


    ///
    /// application data
    ///

    public AppUserInterface getUser() {
        Timber.tag(TAG).d("getUser");

        if (appUser == null) {
            Timber.tag(TAG).w("   ...appUser is null, this should never happen");
            return null;
        } else {
            Timber.tag(TAG).d("   ...returning appUser");
            return appUser;
        }
    }

    public DeviceInfo getDeviceInfo(){
        return deviceDetails.getDeviceInfo();
    }

    public ActiveBatchInterface getActiveBatch() {
        return activeBatch;
    }

    public OffersInterface getOfferLists() {
        return offerLists;
    }



}
