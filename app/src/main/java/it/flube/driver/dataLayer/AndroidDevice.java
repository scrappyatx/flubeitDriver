/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.facebook.device.yearclass.YearClass;
import com.jaredrummler.android.device.DeviceName;

import java.util.UUID;

import it.flube.driver.deviceLayer.googlePlayLocation.GooglePlayLocationWrapper;
import it.flube.driver.deviceLayer.activeBatchForegroundService.ActiveBatchForegroundServiceController;
import it.flube.driver.deviceLayer.AblyConnectionWrapper;
import it.flube.driver.deviceLayer.ActiveBatch;
import it.flube.driver.deviceLayer.AppRemoteConfig;
import it.flube.driver.deviceLayer.AppUser;
import it.flube.driver.deviceLayer.OfferLists;
import it.flube.driver.deviceLayer.UseCaseEngine;
import it.flube.driver.deviceLayer.cloudDatabase.CloudDatabaseFirebaseWrapper;
import it.flube.driver.deviceLayer.DeviceStorageSharedPrefs;
import it.flube.driver.deviceLayer.UserProfile;
import it.flube.driver.deviceLayer.appLogging.AppLoggingTimber;
import it.flube.driver.deviceLayer.cloudAuth.CloudAuthFirebaseWrapper;
import it.flube.driver.deviceLayer.realtimeMessaging.RealtimeActiveBatchMessages;
import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.interfaces.ActiveBatchForegroundServiceInterface;
import it.flube.driver.modelLayer.interfaces.ActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.AppLoggingInterface;
import it.flube.driver.modelLayer.interfaces.AppRemoteConfigInterface;
import it.flube.driver.modelLayer.interfaces.AppUserInterface;
import it.flube.driver.modelLayer.interfaces.CloudAuthInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.CloudStorageInterface;
import it.flube.driver.modelLayer.interfaces.DeviceStorageInterface;
import it.flube.driver.modelLayer.interfaces.LocationTelemetryInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.modelLayer.interfaces.OffersInterface;
import it.flube.driver.modelLayer.interfaces.RealtimeMessagingInterface;
import it.flube.driver.modelLayer.interfaces.UseCaseInterface;
import it.flube.driver.modelLayer.interfaces.UserProfileInterface;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created on 7/3/2017
 * Project : Driver
 */

public class AndroidDevice implements MobileDeviceInterface, DeviceName.Callback {
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

    private static final String DEVICE_PREFS = "DevicePrefs";
    private static final String DEVICE_GUID_FIELD_NAME = "guid";

    private Context applicationContext;

    private MobileDeviceInterface.DeviceInfoRequestComplete deviceInfoResponse;

    private ActiveBatchForegroundServiceInterface foregroundService;
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

    public ActiveBatchInterface getActiveBatch() {
        return ActiveBatch.getInstance();
    }

    public ActiveBatchForegroundServiceInterface getActiveBatchForegroundServiceController(){
        if (foregroundService == null) {
            foregroundService = new ActiveBatchForegroundServiceController(applicationContext);
        }
        return foregroundService;
    }

    public OffersInterface getOfferLists() {
        return OfferLists.getInstance();
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
        return CloudAuthFirebaseWrapper.getInstance();
    }

    public CloudDatabaseInterface getCloudDatabase(){
        return CloudDatabaseFirebaseWrapper.getInstance();
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

    public UseCaseInterface getUseCaseEngine(){
        return UseCaseEngine.getInstance();
    }

    public RealtimeMessagingInterface.Connection getRealtimeConnection() {
        return AblyConnectionWrapper.getInstance();
    }

    //public RealtimeMessagingInterface.OfferChannel getRealtimeOfferMessages() {
    //    return RealtimeOfferMessages.getInstance();
    //}

    //public RealtimeMessagingInterface.BatchChannel getRealtimeBatchMessages() {
    //    return RealtimeBatchMessages.getInstance();
    //}

    public RealtimeMessagingInterface.ActiveBatchChannel getRealtimeActiveBatchMessages(){
        return RealtimeActiveBatchMessages.getInstance();
    }

    public AppUserInterface getUser() {
        return AppUser.getInstance();
    }

    public LocationTelemetryInterface getLocationTelemetry() {
        if (locationTelemetry == null) {
            //locationTelemetry = new LocationEngineWrapper(applicationContext);
            locationTelemetry=new GooglePlayLocationWrapper(applicationContext);
        }
        return locationTelemetry;
    }


    public void deviceInfoRequest(MobileDeviceInterface.DeviceInfoRequestComplete response){
        deviceInfoResponse = response;
        DeviceName.with(applicationContext).request(this);
    }

    public void onFinished(DeviceName.DeviceInfo info, Exception exception) {

        if (exception == null) {
            DeviceInfo thisDevice = new DeviceInfo();

            thisDevice.setDeviceGUID(getDeviceGUID());
            thisDevice.setManufacturer(info.manufacturer);
            thisDevice.setMarketName(info.marketName);
            thisDevice.setModel(info.model);
            thisDevice.setCodeName(info.codename);
            thisDevice.setYearWhenDeviceConsideredHighEnd(Integer.toString(YearClass.get(applicationContext)));
            thisDevice.setVersionAPI(Integer.toString(Build.VERSION.SDK_INT));
            thisDevice.setVersionReleaseAPI(Build.VERSION.RELEASE);

            deviceInfoResponse.deviceInfoSuccess(thisDevice);

        } else {
            deviceInfoResponse.deviceInfoFailure(exception);
        }

    }

    private String getDeviceGUID() {
        //check to see if a device GUID has been saved to shared preferences on this device
        // if NO, then create one and save it
        // read device GUID from shared preferences and return it
        SharedPreferences prefs = applicationContext.getSharedPreferences(DEVICE_PREFS, MODE_PRIVATE);
        if (!prefs.contains(DEVICE_GUID_FIELD_NAME)) {
            SharedPreferences.Editor editor;
            editor = prefs.edit();
            editor.putString(DEVICE_GUID_FIELD_NAME, UUID.randomUUID().toString());
            editor.apply();
        }
        return prefs.getString(DEVICE_GUID_FIELD_NAME, null);
    }



}
