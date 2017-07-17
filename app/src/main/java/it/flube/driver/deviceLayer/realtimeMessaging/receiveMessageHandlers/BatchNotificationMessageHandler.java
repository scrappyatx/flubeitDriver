/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers;

import org.greenrobot.eventbus.EventBus;

import io.ably.lib.realtime.Channel;
import io.ably.lib.types.Message;
import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import timber.log.Timber;

/**
 * Created on 7/15/2017
 * Project : Driver
 */

public class BatchNotificationMessageHandler implements AblyChannel.MessageReceive {
    private static final String TAG = "BatchNotificationMessageHandler";
    private static final String MESSAGE_NAME = "batchNotification";

    public String getName() {
        return MESSAGE_NAME;
    }

    public onMessageBatchNotification getListener(){
        return new onMessageBatchNotification();
    }

    public static class onMessageBatchNotification implements Channel.MessageListener {

        public void onMessage(Message message) {
            Timber.tag(TAG).d("Received batch notification: name -> " + message.name + "  data ->" + message.data);
            EventBus.getDefault().postSticky(new BatchNotificationEvent());
        }
    }

    public static class BatchNotificationEvent {
        public BatchNotificationEvent(){

        }
    }

}
