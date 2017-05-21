/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayerTests.mockData.mockStorage;

import junit.framework.Assert;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepository;
import it.flube.driver.modelLayer.interfaces.driverStorageRepository.DriverStorageRepositoryCallback;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class MockDriverStorage implements DriverStorageRepository {
        private final String TAG = "MockDriverStorage";


    private boolean isAvailable() {
        return true;
    }

    public void load(DriverSingleton driver, DriverStorageRepositoryCallback callback) {
        Assert.assertNotNull(driver);

        driver.setFirstName("Fizzi");
        driver.setLastName("Battlecrank");
        driver.setClientId("12345");
        driver.setEmail("test@example.com");
        driver.setLoaded(true);

        callback.loadDriverSuccess(driver);
    }

    public void save(DriverSingleton driver, DriverStorageRepositoryCallback callback) {
        Assert.assertNotNull(driver);
        callback.saveDriverSuccess();
    }

    public void delete(DriverStorageRepositoryCallback callback) {
        callback.deleteDriverSuccess();
    }
}
