/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.callBacks.messaging;

import io.ably.lib.types.Message;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public interface RsmReceiveMessageCallback {

    //called when a message is received
    void onMessage(String messageData);

}
