/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.driverStorageRepository;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public interface DriverStorageRepositorySaveCallback {
    void saveDriverSuccess();

    void saveDriverFailure(String errorMessage);
}
