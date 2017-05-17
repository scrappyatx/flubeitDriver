/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.repositories.driver;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverNetworkRepositoryCallback;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public interface DriverNetworkRepository {
    void requestDriverProfile(DriverSingleton driver, String requestUrl, String email, String password);
}
