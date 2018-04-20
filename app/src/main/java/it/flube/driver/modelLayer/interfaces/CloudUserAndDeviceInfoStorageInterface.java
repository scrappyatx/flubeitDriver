/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created on 3/13/2018
 * Project : Driver
 */

public interface CloudUserAndDeviceInfoStorageInterface {

    //
    //  USER INFO
    //
    void saveUserAndDeviceInfoRequest(Driver driver, DeviceInfo deviceInfo, SaveResponse response);

    interface SaveResponse {
        void cloudUserAndDeviceInfoSaveComplete();
    }

}
