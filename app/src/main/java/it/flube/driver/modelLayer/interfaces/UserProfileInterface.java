/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;


import it.flube.driver.modelLayer.entities.Driver;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public interface UserProfileInterface {
    void getDriverRequest(String requestUrl, String email, String password, Response response);

    public interface Response {
        void getDriverSuccess(Driver driver);

        void getDriverFailure(String responseMessage);

        void getDriverAuthFailure(String responseMessage);

        void getDriverUserNotADriverFailure();
    }
}
