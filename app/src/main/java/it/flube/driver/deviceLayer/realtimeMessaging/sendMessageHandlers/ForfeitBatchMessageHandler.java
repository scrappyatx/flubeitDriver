/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers;

import org.json.JSONException;
import org.json.JSONObject;

import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import timber.log.Timber;

/**
 * Created on 7/26/2017
 * Project : Driver
 */

public class ForfeitBatchMessageHandler implements AblyChannel.MessageSend {

    private final static String TAG = "ForfeitBatchMessageHandler";
    private final static String MESSAGE_NAME = "forfeitBatch";

    private final String messageName;
    private final JSONObject messageBody;

    private ForfeitBatchMessageHandler(ForfeitBatchMessageHandler.MessageBuilder builder) {
        this.messageName = builder.messageName;
        this.messageBody = builder.messageBody;
    }

    public String getName() {
        return this.messageName;
    }

    public JSONObject getData(){
        return this.messageBody;
    }

    public static class MessageBuilder {
        private final String messageName;
        private final JSONObject messageBody;

        public MessageBuilder(String batchOID) {
            messageName = MESSAGE_NAME;
            messageBody = new JSONObject();
            try {
                messageBody.put("batchOID", batchOID);
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public ForfeitBatchMessageHandler build() {
            return new ForfeitBatchMessageHandler(this);
        }
    }
}
