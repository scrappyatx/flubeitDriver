/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyConnectionState;

/**
 * Created on 5/12/2017
 * Project : Driver
 */

public class ConnectionExceptionEvent {
    private static Exception mEx;

    public ConnectionExceptionEvent(Exception ex) {
        mEx = ex;
    }

    public static Exception getException() { return mEx; }
}
