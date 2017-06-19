/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.testingUseCases.clearDriverSingleton;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/8/2017
 * Project : Driver
 */

public class ClearDriverSingleton implements Runnable {
    private ClearDriverSingletonCallback mCallback;

    public ClearDriverSingleton(ClearDriverSingletonCallback callback) {
        mCallback = callback;
    }
    public void run() {
        DriverSingleton.getInstance().setFirstName("<not loaded>");
        DriverSingleton.getInstance().setLastName("<not loaded>");
        DriverSingleton.getInstance().setClientId("<not loaded>");
        DriverSingleton.getInstance().setEmail("<not loaded>");
        mCallback.clearDriverSingletonSuccess(DriverSingleton.getInstance());
    }
}
