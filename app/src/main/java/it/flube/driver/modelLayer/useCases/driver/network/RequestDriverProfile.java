/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.network;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.interfaces.repositories.driverStorage.DriverNetworkRepository;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class RequestDriverProfile implements Runnable {
    private DriverNetworkRepository mNetwork;
    private String mUsername;
    private String mPassword;
    private String mRequestUrl;

    public RequestDriverProfile(DriverNetworkRepository network, String requestUrl, String username, String password) {
        mNetwork = network;
        mRequestUrl = requestUrl;
        mUsername = username;
        mPassword = password;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public void run() {
        DriverSingleton driver = DriverSingleton.getInstance();
        mNetwork.requestDriverProfile(driver, mRequestUrl, mUsername, mPassword);
    }
}
