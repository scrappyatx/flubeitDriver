/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.callBacks.messaging;

import io.ably.lib.types.ErrorInfo;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public interface RsmSendMessageCallback {
    // called when message is successfully sent
    void onSuccess();

    // called when a message an error occurs when sending a message
    void onError(String errorMessage);
}
