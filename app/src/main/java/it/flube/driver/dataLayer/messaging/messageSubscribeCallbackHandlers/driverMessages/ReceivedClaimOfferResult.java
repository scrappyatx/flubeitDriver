/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageSubscribeCallbackHandlers.driverMessages;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.driverMessages.ReceivedClaimOfferResultMessage;
import it.flube.driver.dataLayer.interfaces.messaging.AblyMessageSubscribeCallback;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedClaimOfferResult implements AblyMessageSubscribeCallback {
    private final String TAG = "RcvdClaimOfferResult";

    public void onMessage(Message message) {
        Log.d(TAG,"Received claim offer result: name -> " + message.name + "  data ->" + message.data);

        //broadcast offers on event bus
        EventBus.getDefault().post(new ReceivedClaimOfferResultMessage());
    }
}
