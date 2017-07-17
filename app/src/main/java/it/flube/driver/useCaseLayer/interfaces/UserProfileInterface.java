/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.interfaces;


import it.flube.driver.modelLayer.Driver;

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
    }
}
