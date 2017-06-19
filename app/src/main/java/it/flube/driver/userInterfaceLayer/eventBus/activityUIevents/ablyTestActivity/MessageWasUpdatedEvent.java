/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.ablyTestActivity;

/**
 * Created on 5/12/2017
 * Project : Driver
 */

public class MessageWasUpdatedEvent {
    String mResultMessage;

    public MessageWasUpdatedEvent(String resultMessage) {
        mResultMessage = resultMessage;
    }

    public String getResultMessage() {
        return mResultMessage;
    }
}
