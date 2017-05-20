/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.storage;

import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverStorageRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created by Bryan on 4/25/2017.
 */

public class LoadDriver implements Runnable {
    private DriverStorageRepository mRepository;
    private DriverStorageRepositoryCallback mCallback;

    public LoadDriver(DriverStorageRepository repository, DriverStorageRepositoryCallback callback) {
        mCallback = callback;
        mRepository = repository;
    }

    public void run() {
        mRepository.load(DriverSingleton.getInstance(), mCallback);
    }
}
