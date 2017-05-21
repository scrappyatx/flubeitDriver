/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.driverStorageRepository;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/7/2017
 * Project : Driver
 */

public interface DriverStorageRepositoryCallback {
    void loadDriverSuccess(DriverSingleton driver);

    void loadDriverFailure(String responseMessage);

    void saveDriverSuccess();

    void deleteDriverSuccess();
}
