/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageSubscribeCallbackHandlers.batchMessages;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.batchMessages.ReceivedAssignedBatchesMessage;
import it.flube.driver.dataLayer.interfaces.messaging.AblyMessageSubscribeCallback;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedAssignedBatches implements AblyMessageSubscribeCallback {
    private final String TAG = "ReceivedAssignedBatches";

    public void onMessage(Message message) {
        Log.d(TAG,"Received assigned batches message : name -> " + message.name + "  data ->" + message.data);

        //broadcast offers on event bus
        EventBus.getDefault().post(new ReceivedAssignedBatchesMessage());
    }
}
