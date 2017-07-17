/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.interfaces;

import it.flube.driver.modelLayer.Driver;

/**
 * Created on 7/5/2017
 * Project : Driver
 */

public interface CloudDatabaseInterface {

    void saveUserRequest(Driver driver, SaveResponse response);


    interface SaveResponse {
        void cloudDatabaseUserSaveComplete();

    }
}