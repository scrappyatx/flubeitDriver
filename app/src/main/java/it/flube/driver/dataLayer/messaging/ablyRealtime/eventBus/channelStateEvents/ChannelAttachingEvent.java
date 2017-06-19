/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.ablyRealtime.eventBus.channelStateEvents;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ChannelAttachingEvent {
    private String mName;
    public ChannelAttachingEvent(String channelName) {
        mName = channelName;
    }

    public String getChannelName() { return mName; }
}
