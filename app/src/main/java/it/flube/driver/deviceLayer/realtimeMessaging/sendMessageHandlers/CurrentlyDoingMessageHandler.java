/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers;

import org.json.JSONException;
import org.json.JSONObject;

import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import timber.log.Timber;

/**
 * Created on 11/9/2017
 * Project : Driver
 */

public class CurrentlyDoingMessageHandler implements
        AblyChannel.MessageSend {


    private final static String TAG = "CurrentlyDoingMessageHandler";
    private final static String MESSAGE_NAME = "currentlyDoing";

    private final String messageName;
    private final JSONObject messageBody;

    private CurrentlyDoingMessageHandler(CurrentlyDoingMessageHandler.MessageBuilder builder) {
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

        public MessageBuilder(String stepTitle) {
            messageName = MESSAGE_NAME;
            messageBody = new JSONObject();
            try {
                messageBody.put("stepTitle", stepTitle);
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public CurrentlyDoingMessageHandler build() {
            return new CurrentlyDoingMessageHandler(this);
        }
    }
}
