/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.batchMessages;

import android.util.Log;

import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessageSubscribeCallback;
import it.flube.driver.modelLayer.interfaces.messaging.RsmReceiveMsgCallbackBatchRemoval;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedBatchRemoval implements AblyMessageSubscribeCallback {
    private final String TAG = "ReceivedBatchRemoval";
    private RsmReceiveMsgCallbackBatchRemoval mCallback;

    public ReceivedBatchRemoval(RsmReceiveMsgCallbackBatchRemoval callback) {
        mCallback = callback;
    }

    public void onMessage(Message message) {
        Log.d(TAG,"Received batch removal message : name -> " + message.name + "  data ->" + message.data);

        //send to callback
        mCallback.receiveMsgBatchRemoval(message.name, message.data.toString());
    }
}
