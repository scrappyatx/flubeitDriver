/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ChannelDetachingEvent {
    private String mName;
    public ChannelDetachingEvent(String channelName) {
        mName = channelName;
    }

    public String getChannelName() { return mName; }
}
