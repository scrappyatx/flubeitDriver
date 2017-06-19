/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.driverStorageRepository;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public interface DriverStorageRepositoryLoadCallback {
    void loadDriverSuccess();

    void loadDriverFailure(String errorMessage);
}
