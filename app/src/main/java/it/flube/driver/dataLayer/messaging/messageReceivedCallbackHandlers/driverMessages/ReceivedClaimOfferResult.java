/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.interfaces.eventBusEvents.ablyRealtime.ablyMessages.driverMessages.ReceivedClaimOfferResultMessage;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessageSubscribeCallback;
import it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks.RsmReceiveMsgCallbackClaimOfferResult;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedClaimOfferResult implements AblyMessageSubscribeCallback {
    private final String TAG = "RcvdClaimOfferResult";
    private RsmReceiveMsgCallbackClaimOfferResult mCallback;

    public ReceivedClaimOfferResult(RsmReceiveMsgCallbackClaimOfferResult callback) {
        mCallback = callback;
    }

    public void onMessage(Message message) {
        Log.d(TAG,"Received claim offer result: name -> " + message.name + "  data ->" + message.data);
        mCallback.receiveMsgClaimOfferResult(message.name, message.data.toString());
        EventBus.getDefault().post(new ReceivedClaimOfferResultMessage());
    }
}
