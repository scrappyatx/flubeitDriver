/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.interfaces;

import it.flube.driver.modelLayer.Driver;

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
