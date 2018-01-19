/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEvents.cloudAuth;

import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created on 11/30/2017
 * Project : Driver
 */

public class CloudAuthUserChangedEvent {

    private Driver driver;

    public CloudAuthUserChangedEvent(Driver driver){
        this.driver = driver;
    }

    public Driver getDriver(){
        return driver;
    }
}
