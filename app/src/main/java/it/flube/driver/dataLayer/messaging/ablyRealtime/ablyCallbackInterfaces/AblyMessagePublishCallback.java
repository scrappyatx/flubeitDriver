/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces;

import io.ably.lib.types.ErrorInfo;

/**
 * Created on 5/14/2017
 * Project : Driver
 */

public interface AblyMessagePublishCallback {
    // Called when the associated operation completes successfully,
    void onSuccess();

    // Called when the associated operation completes with an error.
    void onError(ErrorInfo reason);
}
