/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.appLifecycle;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 11/19/2017
 * Project : Driver
 */

public class UseCaseThingsToDoWhenApplicationPauses implements
    Runnable,
    CloudDatabaseInterface.StopMonitoringResponse {

    private final MobileDeviceInterface device;

    public UseCaseThingsToDoWhenApplicationPauses(MobileDeviceInterface device){
        this.device = device;

    }

    public void run(){
        if (device.getUser().isSignedIn()) {
            device.getCloudDatabase().stopMonitoringRequest(this);
        } else {
            // do nothing
        }
    }

    public void cloudDatabaseStopMonitoringComplete(){
        //do nothing
    }

}
