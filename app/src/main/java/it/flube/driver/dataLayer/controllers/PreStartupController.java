/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.controllers;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryDeleteCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryLoadCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositorySaveCallback;
import it.flube.driver.userInterfaceLayer.eventBus.activityNavigationEvents.GotoMainActivityEventDELETE;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.preStartupActivity.DriverWasUpdatedEvent;
import it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.preStartupActivity.ResultWasUpdatedEvent;
import it.flube.driver.dataLayer.network.toBeDeleted.HttpAblyTokenCallbackDELETE;
import it.flube.driver.dataLayer.network.toBeDeleted.HttpAblyTokenDELETE;
import it.flube.driver.dataLayer.network.HttpDriverProfile;
import it.flube.driver.dataLayer.storage.DriverStorage;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.driverNetwork.DriverNetworkRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryCallbackDELETE;
import it.flube.driver.modelLayer.useCases.driver.testingUseCases.clearDriverSingleton.ClearDriverSingletonCallback;
import it.flube.driver.modelLayer.useCases.driver.testingUseCases.loadDriverTestInfo.LoadDriverTestInfoCallback;
import it.flube.driver.modelLayer.useCases.driver.networkUseCases.requestDriverProfileDELETE.RequestDriverProfileDELETE;
import it.flube.driver.modelLayer.useCases.driver.storageUseCases.deleteDriver.DeleteDriver;
import it.flube.driver.modelLayer.useCases.driver.storageUseCases.loadDriver.LoadDriver;
import it.flube.driver.modelLayer.useCases.driver.storageUseCases.saveDriver.SaveDriver;
import it.flube.driver.modelLayer.useCases.driver.testingUseCases.clearDriverSingleton.ClearDriverSingleton;
import it.flube.driver.modelLayer.useCases.driver.testingUseCases.loadDriverTestInfo.LoadDriverTestInfo;

/**
 * Created on 5/7/2017
 * Project : Driver
 */

public class PreStartupController implements DriverStorageRepositorySaveCallback, DriverStorageRepositoryLoadCallback, DriverStorageRepositoryDeleteCallback,
        LoadDriverTestInfoCallback, ClearDriverSingletonCallback, DriverNetworkRepositoryCallback, HttpAblyTokenCallbackDELETE {
    private final String TAG = "PreStartupController";
    private Context mContext;
    private DriverSingleton mDriver;
    private DriverStorage mDriverStorage;
    private HttpDriverProfile mDriverNetwork;
    private HttpAblyTokenDELETE mHttpAblyTokenDELETE;

    // Use cases this controller can perform
    private LoadDriver mLoadDriver;
    private LoadDriverTestInfo mLoadDriverTestInfo;
    private SaveDriver mSaveDriver;
    private DeleteDriver mDeleteDriver;
    private ClearDriverSingleton mClearDriverSingleton;
    private RequestDriverProfileDELETE mRequestDriverProfileDELETE;

    //network shit
    private String mClientIdURL;
    private String mAblyTokenURL;
    private String mUsername;
    private String mPassword;

    // private initialization methods
    private void initializeStorage() {
        mDriverStorage = new DriverStorage(mContext);
        Log.d(TAG, "Storage initialized");
    }

    private void initializeNetwork() {
        mDriverNetwork = new HttpDriverProfile();
        mDriverNetwork.setCallback(this);

        mHttpAblyTokenDELETE = new HttpAblyTokenDELETE(this);
        mClientIdURL = "https://api.cloudconfidant.com/concierge-oil-service/ably/clientId";
        mUsername = "test@test24.com";
        mPassword = "password";
        mAblyTokenURL = "https://api.cloudconfidant.com/concierge-oil-service/ably/token";
    }

    private void initializeUseCases() {
        mLoadDriver = new LoadDriver(mDriverStorage, this);
        mSaveDriver = new SaveDriver(mDriverStorage, this);
        mDeleteDriver = new DeleteDriver(mDriverStorage, this);
        mLoadDriverTestInfo = new LoadDriverTestInfo(this);
        mClearDriverSingleton = new ClearDriverSingleton(this);
        mRequestDriverProfileDELETE = new RequestDriverProfileDELETE(mDriverNetwork, mClientIdURL, mUsername, mPassword );
        Log.d(TAG, "Use cases initialized");
    }

    //controller constructor

    public PreStartupController(Context context) {
        mContext = context;

        initializeStorage();
        initializeNetwork();
        initializeUseCases();
        Log.d(TAG, "SplashScreenController Controller CREATED");
    }


    // methods the activity can use

    public void loadDriver() {
        Thread t = new Thread(mLoadDriver);
        t.start();
        //mLoadDriver.run();
        Log.d(TAG, "*** loadDriver()");
    }

    public void deleteDriver() {
        Thread t = new Thread(mDeleteDriver);
        t.start();
        //mDeleteDriver.run();
        Log.d(TAG, "*** deleteDriver()");
    }

    public void saveDriver() {
        Thread t = new Thread(mSaveDriver);
        t.start();
        //mSaveDriver.run();
        Log.d(TAG, "*** saveDriver()");
    }

    public void clearDriverSingleton() {
        Thread t = new Thread(mClearDriverSingleton);
        t.start();
        //mClearDriverSingleton.run();
        Log.d(TAG, "*** clear driver Singleton");
    }

    public void loadDriverTestData1() {
        mLoadDriverTestInfo.setTestData("Scrippy","McFailington","12345", "test@shitdontwork.com");

        Thread t = new Thread(mLoadDriverTestInfo);
        t.start();
       //mLoadDriverTestInfo.run();
        Log.d(TAG, "*** loadDriverTestData1()");
    }

    public void loadDriverTestData2() {
        mLoadDriverTestInfo.setTestData("Scrappy","McPassington","45678", "test@test.com");

        Thread t = new Thread(mLoadDriverTestInfo);
        t.start();
        //mLoadDriverTestInfo.run();
        Log.d(TAG, "*** loadDriverTestData2()");
    }

    public void requestDriverProfile() {
        mRequestDriverProfileDELETE.setUsername(DriverSingleton.getInstance().getEmail());
        mRequestDriverProfileDELETE.setPassword("password");

        Thread t = new Thread(mRequestDriverProfileDELETE);
        t.start();
        //mRequestDriverProfileDELETE.run();
        Log.d(TAG, "*** request driver profile");
    }

    public void requestAblyToken() {
        mHttpAblyTokenDELETE.requestAblyTokenAsync(mAblyTokenURL,DriverSingleton.getInstance().getClientId());
        Log.d(TAG, "*** request Ably Token");
    }

    public void goToStartupActivity() {
        EventBus.getDefault().post(new GotoMainActivityEventDELETE());
        Log.d(TAG, "*** posted GotoMainActivityEventDELETE on EventBus");
    }

    // Use case callbacks
    public void requestAblyTokenSuccess(String token) {
        Log.d(TAG, "*** requestAblyTokenAsync SUCCESS!");
        EventBus.getDefault().post(new ResultWasUpdatedEvent("request AblyToken SUCCESS! Token -> " + token));
    }

    public void requestAblyTokenFailure(String errorMessage) {
        Log.d(TAG, "*** requestAblyTokenAsync FAILURE");
        EventBus.getDefault().post(new ResultWasUpdatedEvent("request AblyToken FAILURE -> " + errorMessage));
    }

    public void saveDriverSuccess() {
        Log.d(TAG, "*** Driver data saved to device");
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> Saved driver data to shared preferences"));
    }

    public void saveDriverFailure(String errorMessage) {
        Log.d(TAG, "*** Driver data saved to device");
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> Saved driver data error --> " + errorMessage));
    }

    public void loadDriverSuccess() {
        Log.d(TAG, "*** Driver data loaded from device");
        EventBus.getDefault().post(new DriverWasUpdatedEvent(DriverSingleton.getInstance()));
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> loaded driver data from shared preferences"));
    }

    public void loadDriverFailure(String responseMessage) {
        Log.d(TAG, "*** Driver data couldn't be loaded from device -> " + responseMessage);
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> Driver data couldn't be loaded from shared preferences : " + responseMessage));
    }

    public void deleteDriverSuccess() {
        Log.d(TAG, "*** Driver data deleted from device");
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> Driver data deleted from shared preferences"));
    }

    public void loadDriverTestInfoSuccess(DriverSingleton driver) {
        Log.d(TAG, "*** Loaded test driver data");
        EventBus.getDefault().post(new DriverWasUpdatedEvent(DriverSingleton.getInstance()));
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> Loaded test data into driver singleton"));
    }

    public void clearDriverSingletonSuccess(DriverSingleton driver) {
        Log.d(TAG, "*** clear driver singleton");
        EventBus.getDefault().post(new DriverWasUpdatedEvent(DriverSingleton.getInstance()));
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> cleared data from driver singleton"));
    }

    public void requestDriverProfileSuccess() {
        Log.d(TAG, "*** HTTP request driver profile SUCCESS");
        EventBus.getDefault().post(new DriverWasUpdatedEvent(DriverSingleton.getInstance()));
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> HTTP request for driver profile SUCCESS"));
    }

    public void requestDriverProfileFailure(String responseMessage) {
        Log.d(TAG, "*** HTTP request driver profile FAILURE --> " + responseMessage);
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> HTTP request for driver profile FAILURE -> " + responseMessage));
    }

    public void requestDriverProfileAuthenticationFailure(String responseMessage) {
        Log.d(TAG, "*** HTTP request driver profile AUTHENTICATION FAILURE --> " + responseMessage);
        EventBus.getDefault().post(new ResultWasUpdatedEvent("Last action -> HTTP request for driver profile FAILURE -> " + responseMessage));
    }
}
