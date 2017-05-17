/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.controllers;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import it.flube.driver.dataLayer.interfaces.eventBusEvents.activityTransition.GotoMainActivityEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.preStartupActivity.DriverWasUpdatedEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.preStartupActivity.ResultWasUpdatedEvent;
import it.flube.driver.dataLayer.interfaces.network.HttpAblyTokenCallback;
import it.flube.driver.dataLayer.network.HttpAblyTokenDELETE;
import it.flube.driver.dataLayer.network.HttpDriverProfile;
import it.flube.driver.dataLayer.storage.DriverStorage;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverNetworkRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverStorageRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.callBacks.useCase.driver.ClearDriverSingletonCallback;
import it.flube.driver.modelLayer.interfaces.callBacks.useCase.driver.LoadDriverTestInfoCallback;
import it.flube.driver.modelLayer.useCases.driver.network.RequestDriverProfile;
import it.flube.driver.modelLayer.useCases.driver.storage.DeleteDriver;
import it.flube.driver.modelLayer.useCases.driver.storage.LoadDriver;
import it.flube.driver.modelLayer.useCases.driver.storage.SaveDriver;
import it.flube.driver.modelLayer.useCases.driver.testing.ClearDriverSingleton;
import it.flube.driver.modelLayer.useCases.driver.testing.LoadDriverTestInfo;

/**
 * Created on 5/7/2017
 * Project : Driver
 */

public class PreStartupController implements DriverStorageRepositoryCallback, LoadDriverTestInfoCallback, ClearDriverSingletonCallback, DriverNetworkRepositoryCallback, HttpAblyTokenCallback {
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
    private RequestDriverProfile mRequestDriverProfile;

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
        mDriverNetwork = new HttpDriverProfile(this);
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
        mRequestDriverProfile = new RequestDriverProfile(mDriverNetwork, mClientIdURL, mUsername, mPassword );
        Log.d(TAG, "Use cases initialized");
    }

    //controller constructor

    public PreStartupController(Context context) {
        mContext = context;

        initializeStorage();
        initializeNetwork();
        initializeUseCases();
        Log.d(TAG, "StartupController Controller CREATED");
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
        mRequestDriverProfile.setUsername(DriverSingleton.getInstance().getEmail());
        mRequestDriverProfile.setPassword("password");

        Thread t = new Thread(mRequestDriverProfile);
        t.start();
        //mRequestDriverProfile.run();
        Log.d(TAG, "*** request driver profile");
    }

    public void requestAblyToken() {
        mHttpAblyTokenDELETE.requestAblyTokenAsync(mAblyTokenURL,DriverSingleton.getInstance().getClientId());
        Log.d(TAG, "*** request Ably Token");
    }

    public void goToStartupActivity() {
        EventBus.getDefault().post(new GotoMainActivityEvent());
        Log.d(TAG, "*** posted GotoMainActivityEvent on EventBus");
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

    public void loadDriverSuccess(DriverSingleton driver) {
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

    public void requestDriverProfileSuccess(DriverSingleton driver) {
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
