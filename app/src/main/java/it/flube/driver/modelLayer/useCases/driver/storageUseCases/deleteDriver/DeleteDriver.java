/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.storageUseCases.deleteDriver;

import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryCallbackDELETE;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryDeleteCallback;

/**
 * Created on 5/7/2017
 * Project : Driver
 */

public class DeleteDriver implements Runnable {
    private DriverStorageRepository mRepository;
    private DriverStorageRepositoryDeleteCallback mCallback;

    public DeleteDriver(DriverStorageRepository repository, DriverStorageRepositoryDeleteCallback callback) {
        mCallback = callback;
        mRepository = repository;
    }

    public void run() {
        mRepository.delete(mCallback);
    }
}
