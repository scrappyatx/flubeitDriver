/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.driverNetwork;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public interface DriverNetworkRepository {
    void requestDriverProfile(String requestUrl, String email, String password);

    void setCallback(DriverNetworkRepositoryCallback callback);
}
