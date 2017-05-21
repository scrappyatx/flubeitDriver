/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.testingUseCases.clearDriverSingleton;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/8/2017
 * Project : Driver
 */

public interface ClearDriverSingletonCallback {
    public void clearDriverSingletonSuccess(DriverSingleton driver);
}
