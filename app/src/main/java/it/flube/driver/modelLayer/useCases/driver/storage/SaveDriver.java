/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.storage;

import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverStorageRepositoryCallback;
import it.flube.driver.modelLayer.interfaces.repositories.driver.DriverStorageRepository;
import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created by Bryan on 4/25/2017.
 */

public class SaveDriver implements Runnable {
    private DriverStorageRepository mRepository;
    private DriverStorageRepositoryCallback mCallback;

    public SaveDriver(DriverStorageRepository repository, DriverStorageRepositoryCallback callback) {
        mCallback = callback;
        mRepository = repository;
    }

    public void run() {
        mRepository.save(DriverSingleton.getInstance(), mCallback );
    }
}
