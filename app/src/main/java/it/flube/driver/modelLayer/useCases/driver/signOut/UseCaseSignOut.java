/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.signOut;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryDeleteCallback;
import it.flube.driver.modelLayer.useCases.driver.signIn.UseCaseSignInFromDeviceStorageCallback;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public class UseCaseSignOut implements Runnable, DriverStorageRepositoryDeleteCallback {

    private DriverStorageRepository mStorage;
    private UseCaseSignOutCallback mCallback;

    public UseCaseSignOut(DriverStorageRepository storage, UseCaseSignOutCallback callback) {
        mStorage = storage;
        mCallback = callback;
    }

    public void run() {
        DriverSingleton.getInstance().clear();
        mStorage.delete(this);
    }

    public void deleteDriverSuccess() {
        mCallback.signOutSuccess();
    }

}
