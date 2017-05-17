/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging;

import io.ably.lib.realtime.Channel;
import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.interfaces.messaging.AblyMessageSubscribeCallback;

/**
 * Created on 5/13/2017
 * Project : Driver
 */

public class AblyMessageSubscribeListener implements Channel.MessageListener {
    private AblyMessageSubscribeCallback mMessageCallback;

    public AblyMessageSubscribeListener(AblyMessageSubscribeCallback callback) {
        mMessageCallback = callback;
    }

    // Called when one or more messages are received
    public void onMessage(Message message) {
        mMessageCallback.onMessage(message);
    }
}
