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

public class UseCaseThingsToDoWhenApplicationResumes implements
        Runnable,
        CloudDatabaseInterface.StartMonitoringResponse {

    private final MobileDeviceInterface device;

    public UseCaseThingsToDoWhenApplicationResumes(MobileDeviceInterface device){
        this.device = device;
    }
    public void run(){
        if (device.getUser().isSignedIn()){
            device.getCloudDatabase().startMonitoringRequest(this);
        } else {
            //do nothing
        }

    }

    public void cloudDatabaseStartMonitoringComplete(){
        //do nothing
    }
}
