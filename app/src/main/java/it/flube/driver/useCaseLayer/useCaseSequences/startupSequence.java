/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.useCaseSequences;

import it.flube.driver.useCaseLayer.UseCaseInitialization;
import it.flube.driver.useCaseLayer.UseCaseSignInFromDeviceStorage;
import it.flube.driver.useCaseLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 7/15/2017
 * Project : Driver
 */

public class startupSequence implements Runnable, UseCaseInitialization.Response {

    private UseCaseInitialization initialization;
    private UseCaseSignInFromDeviceStorage signIn;

    public startupSequence(MobileDeviceInterface device, UseCaseSignInFromDeviceStorage.Response response) {
        initialization = new UseCaseInitialization(device, this);
        signIn = new UseCaseSignInFromDeviceStorage(device, response);
    }

    public void run(){
        initialization.run();
    }

    public void useCaseInitializationComplete() {
        signIn.run();
    }
}
