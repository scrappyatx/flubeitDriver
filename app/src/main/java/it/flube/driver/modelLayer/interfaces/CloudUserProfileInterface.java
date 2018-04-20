/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created on 3/13/2018
 * Project : Driver
 */

public interface CloudUserProfileInterface {

    void getUserProfileRequest(String clientId, String email, CloudUserProfileInterface.UserProfileResponse response);

    interface UserProfileResponse {
        void cloudGetUserProfileSuccess(Driver driver);

        void cloudGetUserProfileNotFound();

        void cloudGetUserProfileAccessDenied();
    }

}
