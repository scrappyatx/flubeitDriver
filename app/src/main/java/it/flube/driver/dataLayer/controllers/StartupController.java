/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.controllers;

import android.content.Context;
import android.util.Log;

import com.rollbar.android.Rollbar;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activityTransition.GotoLoginActivityEvent;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.activityTransition.GotoMainActivityEvent;
import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverStorageRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.callBacks.useCase.driver.ClearDriverSingletonCallback;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.useCases.driver.storage.LoadDriver;
import it.flube.driver.modelLayer.useCases.driver.testing.ClearDriverSingleton;
import it.flube.driver.dataLayer.storage.DriverStorage;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Bryan on 4/30/2017.
 */

public class StartupController implements DriverStorageRepositoryCallback, ClearDriverSingletonCallback {
    private final String TAG = "StartupController";
    private Context mContext;
    private DriverSingleton mDriver;
    private DriverStorage mDriverStorage;

    // Use cases this controller can perform
    private LoadDriver mLoadDriver;
    private ClearDriverSingleton mClearDriverSingleton;


    // private initialization methods
    private void initializeStorage() {
        mDriverStorage = new DriverStorage(mContext);
        Log.d(TAG, "Storage initialized");
    }


    private void initializeUseCases() {
        mLoadDriver = new LoadDriver(mDriverStorage, this);
        mClearDriverSingleton = new ClearDriverSingleton(this);
        Log.d(TAG, "Use cases initialized");
    }

    //controller constructor

    public StartupController(Context context) {
        mContext = context;

        initializeStorage();
        initializeUseCases();
        Log.d(TAG, "StartupController Controller CREATED");
    }

    public void startupSequence() {
        // clear the driver singleton
        Thread t = new Thread(mClearDriverSingleton);
        t.start();
    }

    // use case callbacks

    public void loadDriverSuccess(DriverSingleton driver) {
        Log.d(TAG, "*** Driver data loaded from device");
        // need to go to main activity
        EventBus.getDefault().post(new GotoMainActivityEvent());
        Log.d(TAG, "*** posted GotoMainActivityEvent on EventBus");
    }

    public void loadDriverFailure(String responseMessage) {
        Log.d(TAG, "*** Driver data couldn't be loaded from device -> " + responseMessage);
        // need to go to login activity
        EventBus.getDefault().post(new GotoLoginActivityEvent());
        Log.d(TAG, "*** posted GotoLoginActivityEvent on EventBus");
    }

    public void deleteDriverSuccess() {

    }


    public void clearDriverSingletonSuccess(DriverSingleton driver) {
        Log.d(TAG, "*** driver singleton CLEARED");
        //now try to load the driver
        Thread t = new Thread(mLoadDriver);
        t.start();
    }

    public void saveDriverSuccess() {

    }

}