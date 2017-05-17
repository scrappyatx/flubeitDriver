/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.repositories.driver;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.callBacks.repositories.driver.DriverStorageRepositoryCallback;

/**
 * Created by Bryan on 5/2/2017.
 */

public interface DriverStorageRepository {
    void load(DriverSingleton driver, DriverStorageRepositoryCallback callback);

    void save(DriverSingleton driver, DriverStorageRepositoryCallback callback);

    void delete(DriverStorageRepositoryCallback callback);

}
