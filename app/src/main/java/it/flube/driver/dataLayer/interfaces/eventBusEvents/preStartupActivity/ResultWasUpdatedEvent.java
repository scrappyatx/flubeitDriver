/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.eventBusEvents.preStartupActivity;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created on 5/9/2017
 * Project : Driver
 */

public class ResultWasUpdatedEvent {
    String mResultMessage;

    public ResultWasUpdatedEvent(String resultMessage) {
        mResultMessage = resultMessage;
    }

    public String getResultMessage() {
        return mResultMessage;
    }
}
