/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces;

import io.ably.lib.types.Message;

/**
 * Created on 5/14/2017
 * Project : Driver
 */

public interface AblyMessageSubscribeCallback {
    void onMessage(Message message);
}
