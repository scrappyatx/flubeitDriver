/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.storageUseCases.loadDriver;

import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryCallbackDELETE;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryLoadCallback;

/**
 * Created by Bryan on 4/25/2017.
 */

public class LoadDriver implements Runnable {
    private DriverStorageRepository mRepository;
    private DriverStorageRepositoryLoadCallback mCallback;

    public LoadDriver(DriverStorageRepository repository, DriverStorageRepositoryLoadCallback callback) {
        mCallback = callback;
        mRepository = repository;
    }

    public void run() {
        mRepository.load(mCallback);
    }
}
