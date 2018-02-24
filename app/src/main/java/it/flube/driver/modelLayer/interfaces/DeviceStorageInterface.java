/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created by Bryan on 5/2/2017.
 */

public interface DeviceStorageInterface {
    void loadRequest(DeviceStorageInterface.LoadResponse callback);

    void saveRequest(Driver driver, DeviceStorageInterface.SaveResponse callback);

    void deleteRequest(DeviceStorageInterface.DeleteResponse callback);

    interface DeleteResponse {
        void deviceStorageDeleteComplete();
    }

    interface LoadResponse {
        void deviceStorageLoadSuccess(Driver driver);

        void deviceStorageLoadFailure(String errorMessage);
    }

    interface SaveResponse {
        void deviceStorageSaveComplete();
    }

}
