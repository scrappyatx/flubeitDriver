/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.storageUseCases.saveDriver;

import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryCallbackDELETE;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositorySaveCallback;

/**
 * Created by Bryan on 4/25/2017.
 */

public class SaveDriver implements Runnable {
    private DriverStorageRepository mRepository;
    private DriverStorageRepositorySaveCallback mCallback;

    public SaveDriver(DriverStorageRepository repository, DriverStorageRepositorySaveCallback callback) {
        mCallback = callback;
        mRepository = repository;
    }

    public void run() {
        mRepository.save(mCallback );
    }
}
