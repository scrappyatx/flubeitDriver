/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages;

import android.util.Log;

import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessageSubscribeCallback;
import it.flube.driver.deprecated.realtimeMessaging.RsmReceiveMsgCallbackBatchNotification;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedBatchNotification implements AblyMessageSubscribeCallback {
    private final String TAG = "RcvdBatchNotification";
    private RsmReceiveMsgCallbackBatchNotification mCallback;

    public ReceivedBatchNotification(RsmReceiveMsgCallbackBatchNotification callback) {
        mCallback = callback;
    }

    public void onMessage(Message message) {
        Log.d(TAG,"Received batch notification message : name -> " + message.name + "  data ->" + message.data);

        mCallback.receiveMsgBatchNotification(message.name, message.data.toString());

    }
}
