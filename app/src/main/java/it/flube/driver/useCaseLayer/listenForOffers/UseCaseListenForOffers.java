/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.listenForOffers;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.signInAndSignOut.UseCaseThingsToDoAfterSignIn;

/**
 * Created on 10/5/2017
 * Project : Driver
 */

public class UseCaseListenForOffers implements Runnable {

    private final MobileDeviceInterface device;

    public UseCaseListenForOffers(MobileDeviceInterface device){
        this.device = device;
    }

    public void run(){
        device.getCloudDatabase().startMonitoring();
    }
}
