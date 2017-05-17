/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import io.ably.lib.realtime.ChannelStateListener;
import io.ably.lib.realtime.ConnectionStateListener;
import it.flube.driver.dataLayer.interfaces.messaging.AblyChannelCallback;
import it.flube.driver.dataLayer.interfaces.messaging.AblyConnectionCallback;

/**
 * Created on 5/13/2017
 * Project : Driver
 */

public class AblyChannelStateListener implements ChannelStateListener {
    private AblyChannelCallback mCallback;

    public AblyChannelStateListener(AblyChannelCallback callback) {
        mCallback = callback;
    }

    public void onChannelStateChanged(ChannelStateChange channelStateChange) {
        switch (channelStateChange.current) {
            case initialized:
                mCallback.onChannelCallbackInitialized();
                break;
            case attaching:
                mCallback.onChannelCallbackAttaching();
                break;
            case attached:
                // if resumed = TRUE, then message continuity was preserved, NO MESSAGES HAVE BEEN LOST
                // if resumed = FALSE, then some messages have been missed by the client, MESSAGES HAVE BEEN LOST
                mCallback.onChannelCallbackAttached(channelStateChange.resumed);
                break;
            case detaching:
                mCallback.onChannelCallbackDetaching();
                break;
            case detached:
                mCallback.onChannelCallbackDetached();
                break;
            case suspended:
                mCallback.onChannelCallbackSuspended();
                break;
            case failed:
                mCallback.onChannelCallbackFailed(channelStateChange.reason);
                break;
        }
    }
}



