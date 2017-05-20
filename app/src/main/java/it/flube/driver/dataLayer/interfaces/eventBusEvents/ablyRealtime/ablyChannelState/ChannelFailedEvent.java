/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyChannelState;

import io.ably.lib.types.ErrorInfo;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ChannelFailedEvent {
    private String mName;
    private ErrorInfo mErrorInfo;

    public ChannelFailedEvent(String channelName, ErrorInfo e) {
        mName = channelName;
        mErrorInfo = e;
    }

    public String getChannelName() { return mName; }

    public ErrorInfo getErrorInfo() { return mErrorInfo; }
}
