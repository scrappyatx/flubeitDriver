/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.driverNetwork;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/7/2017
 * Project : Driver
 */

public interface DriverNetworkRepositoryCallback {
    void requestDriverProfileSuccess();

    void requestDriverProfileFailure(String responseMessage);

    void requestDriverProfileAuthenticationFailure(String responseMessage);
}
