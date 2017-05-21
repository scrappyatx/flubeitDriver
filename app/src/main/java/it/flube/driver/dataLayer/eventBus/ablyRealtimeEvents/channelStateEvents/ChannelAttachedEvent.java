/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.eventBus.ablyRealtimeEvents.channelStateEvents;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ChannelAttachedEvent {
    private String mName;
    public ChannelAttachedEvent(String channelName) {
        mName = channelName;
    }

    public String getChannelName() { return mName; }
}
