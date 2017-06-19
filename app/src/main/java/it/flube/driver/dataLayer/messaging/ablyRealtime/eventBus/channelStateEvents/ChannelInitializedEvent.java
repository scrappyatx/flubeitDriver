/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.ablyRealtime.eventBus.channelStateEvents;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ChannelInitializedEvent {
    private String mName;
    public ChannelInitializedEvent(String channelName) {
        mName = channelName;
    }

    public String getChannelName() { return mName; }
}
