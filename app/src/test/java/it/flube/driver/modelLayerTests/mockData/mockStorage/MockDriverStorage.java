/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayerTests.mockData.mockStorage;

import junit.framework.Assert;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryCallbackDELETE;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryDeleteCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryLoadCallback;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositorySaveCallback;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class MockDriverStorage implements DriverStorageRepository {
        private final String TAG = "MockDriverStorage";


    private boolean isAvailable() {
        return true;
    }

    public void load(DriverStorageRepositoryLoadCallback callback) {
        DriverSingleton driver = DriverSingleton.getInstance();

        Assert.assertNotNull(driver);

        driver.setFirstName("Fizzi");
        driver.setLastName("Battlecrank");
        driver.setClientId("12345");
        driver.setEmail("test@example.com");
        driver.setSignedIn(true);

        callback.loadDriverSuccess();
    }


    public void save(DriverStorageRepositorySaveCallback callback) {
        callback.saveDriverSuccess();
    }

    public void delete(DriverStorageRepositoryDeleteCallback callback) {
        callback.deleteDriverSuccess();
    }
}
