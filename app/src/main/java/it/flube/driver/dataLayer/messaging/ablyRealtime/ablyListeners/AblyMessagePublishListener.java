/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.ablyRealtime.ablyListeners;

import io.ably.lib.realtime.CompletionListener;
import io.ably.lib.types.ErrorInfo;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessagePublishCallback;

/**
 * Created on 5/14/2017
 * Project : Driver
 */

public class AblyMessagePublishListener implements CompletionListener {
    // Called when the associated operation completes successfully,
    private AblyMessagePublishCallback mPublishCallback;

    public AblyMessagePublishListener(AblyMessagePublishCallback callback) {
        mPublishCallback = callback;
    }

    public void onSuccess() {
        mPublishCallback.onSuccess();
    }

    // Called when the associated operation completes with an error.
    public void onError(ErrorInfo reason) {
        mPublishCallback.onError(reason);
    }
}
