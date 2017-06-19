/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.eventBus.activityUIevents.loginActivity;

/**
 * Created on 6/2/2017
 * Project : Driver
 */

public class LoginResultWasUpdatedEvent {
    String mResultMessage;

    public LoginResultWasUpdatedEvent(String resultMessage) {
            mResultMessage = resultMessage;
        }

    public String getResultMessage() {
            return mResultMessage;
        }
}
