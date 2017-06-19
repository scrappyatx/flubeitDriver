/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.signIn;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.entities.OfferListSingleton;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryLoadCallback;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public class UseCaseSignInFromDeviceStorage implements Runnable, DriverStorageRepositoryLoadCallback {
    private DriverStorageRepository mStorage;
    private UseCaseSignInFromDeviceStorageCallback mCallback;

    public UseCaseSignInFromDeviceStorage(DriverStorageRepository storage, UseCaseSignInFromDeviceStorageCallback callback) {
        mStorage = storage;
        mCallback = callback;
    }


    public void run() {
        DriverSingleton.getInstance().clear();
        OfferListSingleton.getInstance().getOfferList().clear();

        mStorage.load(this);
    }


    ///
    /// Callbacks --> DriverStorageRepositoryLoadCallbacks

    public void loadDriverSuccess() {
        DriverSingleton.getInstance().setSignedIn(true);
        mCallback.SignInFromDeviceStorageSuccess();
    }

    public void loadDriverFailure(String errorMessage) {
        DriverSingleton.getInstance().setSignedIn(false);
        mCallback.SignInFromDeviceStorageFailure(errorMessage);
    }

}
