/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import io.ably.lib.realtime.Channel;
import io.ably.lib.types.Message;
import it.flube.driver.dataLayer.messaging.ablyRealtime.ablyCallbackInterfaces.AblyMessageSubscribeCallback;
import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import it.flube.driver.useCaseLayer.interfaces.realtimeMessaging.RsmReceiveMsgCallbackBatchRemoval;
import timber.log.Timber;

/**
 * Created on 7/15/2017
 * Project : Driver
 */

public class BatchRemovalMessageHandler implements AblyChannel.MessageReceive {
    private static final String TAG = "BatchRemovalMessageHandler";
    private static final String MESSAGE_NAME = "batchRemoval";

    public String getName() {
        return MESSAGE_NAME;
    }

    public onMessageBatchRemove getListener(){
        return new onMessageBatchRemove();
    }

    public static class onMessageBatchRemove implements Channel.MessageListener {

        public void onMessage(Message message) {
            Timber.tag(TAG).d("Received batch remove : name -> " + message.name + "  data ->" + message.data);
            EventBus.getDefault().postSticky(new BatchRemoveEvent());
        }
    }

    public static class BatchRemoveEvent {
        public BatchRemoveEvent(){

        }
    }
}
