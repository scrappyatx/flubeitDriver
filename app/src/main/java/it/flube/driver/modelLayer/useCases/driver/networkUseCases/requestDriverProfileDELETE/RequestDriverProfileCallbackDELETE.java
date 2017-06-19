/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.networkUseCases.requestDriverProfileDELETE;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public interface RequestDriverProfileCallbackDELETE {
    void requestDriverProfileSuccess(DriverSingleton driver);
    void requestDriverProfileFailed(String errorMessage);
}
