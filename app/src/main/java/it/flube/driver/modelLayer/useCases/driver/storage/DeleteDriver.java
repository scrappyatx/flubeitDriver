/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.storage;

import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverStorageRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;

/**
 * Created on 5/7/2017
 * Project : Driver
 */

public class DeleteDriver implements Runnable {
    private DriverStorageRepository mRepository;
    private DriverStorageRepositoryCallback mCallback;

    public DeleteDriver(DriverStorageRepository repository, DriverStorageRepositoryCallback callback) {
        mCallback = callback;
        mRepository = repository;
    }

    public void run() {
        mRepository.delete(mCallback);
    }
}
