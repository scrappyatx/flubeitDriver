/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyTestActivity;

/**
 * Created on 5/12/2017
 * Project : Driver
 */

public class ConnectionWasUpdatedEvent {
    String mResultMessage;

    public ConnectionWasUpdatedEvent(String resultMessage) {
        mResultMessage = resultMessage;
    }

    public String getResultMessage() {
        return mResultMessage;
    }
}
