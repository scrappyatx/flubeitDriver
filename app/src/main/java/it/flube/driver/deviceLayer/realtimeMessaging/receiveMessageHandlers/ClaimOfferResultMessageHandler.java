/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.ably.lib.realtime.Channel;
import io.ably.lib.types.Message;
import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import timber.log.Timber;


/**
 * Created on 7/15/2017
 * Project : Driver
 */

public class ClaimOfferResultMessageHandler implements AblyChannel.MessageReceive {
    private static final String TAG = "ClaimOfferResultMessageHandler";
    private static final String MESSAGE_NAME = "claimOfferResult";

    public String getName() {
        return MESSAGE_NAME;
    }

    public onMessageClaimOfferResult getListener() {
        return new onMessageClaimOfferResult();

    }
    public static class onMessageClaimOfferResult implements Channel.MessageListener {

        public void onMessage(Message message) {
            Timber.tag(TAG).d("Received claim offer result message : name -> " + message.name + "  data ->" + message.data);
            EventBus.getDefault().postSticky(new ClaimOfferResultEvent());
        }
    }

    public static class ClaimOfferResultEvent {
        public ClaimOfferResultEvent(){

        }
    }
}
