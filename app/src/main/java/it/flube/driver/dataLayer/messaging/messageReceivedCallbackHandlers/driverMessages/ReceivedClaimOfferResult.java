/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.messaging.messageReceivedCallbackHandlers.driverMessages;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.ably.lib.types.Message;
import it.flube.driver.deviceLayer.realtimeMessaging.eventBus.driverMessageEvents.RealtimeMessageClaimOfferResultEvent;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessageSubscribeCallback;
import it.flube.driver.useCaseLayer.interfaces.realtimeMessaging.RtmReceiveMsgClaimOfferResult;

/**
 * Created on 5/17/2017
 * Project : Driver
 */

public class ReceivedClaimOfferResult implements AblyMessageSubscribeCallback {
    private final String TAG = "RcvdClaimOfferResult";
    private RtmReceiveMsgClaimOfferResult mCallback;

    public ReceivedClaimOfferResult(RtmReceiveMsgClaimOfferResult callback) {
        mCallback = callback;
    }

    public void onMessage(Message message) {
        Log.d(TAG,"Received claim offer result: name -> " + message.name + "  data ->" + message.data);
        mCallback.receiveMsgClaimOfferResult(message.name, message.data.toString());
        EventBus.getDefault().post(new RealtimeMessageClaimOfferResultEvent());
    }
}
