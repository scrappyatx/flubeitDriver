/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.ablyRealtime.ablyListeners;

import io.ably.lib.realtime.ChannelStateListener;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyChannelCallback;

/**
 * Created on 5/13/2017
 * Project : Driver
 */

public class AblyChannelStateListener implements ChannelStateListener {
    private AblyChannelCallback mCallback;
    private String mChannelName;

    public AblyChannelStateListener(String channelName, AblyChannelCallback callback) {
        mChannelName = channelName;
        mCallback = callback;
    }

    public void onChannelStateChanged(ChannelStateChange channelStateChange) {
        switch (channelStateChange.current) {
            case initialized:
                mCallback.onChannelCallbackInitialized(mChannelName);
                break;
            case attaching:
                mCallback.onChannelCallbackAttaching(mChannelName);
                break;
            case attached:
                // if resumed = TRUE, then message continuity was preserved, NO MESSAGES HAVE BEEN LOST
                // if resumed = FALSE, then some messages have been missed by the client, MESSAGES HAVE BEEN LOST
                mCallback.onChannelCallbackAttached(mChannelName, channelStateChange.resumed);
                break;
            case detaching:
                mCallback.onChannelCallbackDetaching(mChannelName);
                break;
            case detached:
                mCallback.onChannelCallbackDetached(mChannelName);
                break;
            case suspended:
                mCallback.onChannelCallbackSuspended(mChannelName);
                break;
            case failed:
                mCallback.onChannelCallbackFailed(mChannelName, channelStateChange.reason);
                break;
        }
    }
}



