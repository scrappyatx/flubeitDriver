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

    void connectRequest(AppRemoteConfigInterface appConfig, ConnectResponse response);

    interface ConnectResponse {
        void cloudAuthConnectComplete();
    }

    void disconnect();

    void signInRequest(Driver driver, SignInResponse response);

    void signOutRequest(SignOutResponse response);

    interface SignInResponse {
        void signInUserCloudAuthSuccess();

        void signInUserCloudAuthFailure();
    }

    interface SignOutResponse {
        void signOutUserCloudAuthComplete();
    }
}
