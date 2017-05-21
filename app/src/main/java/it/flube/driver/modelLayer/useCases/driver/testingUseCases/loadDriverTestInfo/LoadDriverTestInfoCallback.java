/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.testingUseCases.loadDriverTestInfo;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created by Bryan on 5/2/2017.
 */

public interface LoadDriverTestInfoCallback {
    void loadDriverTestInfoSuccess(DriverSingleton driver);
}
