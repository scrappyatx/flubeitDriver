/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.eventBusEvents.activities.preStartupActivity;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/9/2017
 * Project : Driver
 */

public class DriverWasUpdatedEvent {
    public DriverWasUpdatedEvent(DriverSingleton driver) {

    }

    public DriverSingleton getDriver() {
        return DriverSingleton.getInstance();
    }
}
