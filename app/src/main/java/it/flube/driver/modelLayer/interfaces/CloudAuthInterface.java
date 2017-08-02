/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.Driver;

/**
 * Created on 6/25/2017
 * Project : Driver
 */

public interface CloudAuthInterface {

    void signInRequest(Driver driver, AppRemoteConfigInterface appConfig, SignInResponse response);

    void signOutRequest(SignOutResponse response);

    interface SignInResponse {
        void signInUserCloudAuthComplete();
    }

    interface SignOutResponse {
        void signOutUserCloudAuthComplete();
    }
}
