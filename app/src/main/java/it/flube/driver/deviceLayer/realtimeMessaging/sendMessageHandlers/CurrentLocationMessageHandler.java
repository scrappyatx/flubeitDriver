/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers;

import org.json.JSONException;
import org.json.JSONObject;

import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import timber.log.Timber;

/**
 * Created on 7/15/2017
 * Project : Driver
 */

public class CurrentLocationMessageHandler implements
        AblyChannel.MessageSend {

    private final static String TAG = "CurrentLocationMessageHandler";
    private final static String MESSAGE_NAME = "location";

    private final String messageName;
    private final JSONObject messageBody;

    private CurrentLocationMessageHandler(CurrentLocationMessageHandler.MessageBuilder builder) {
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

        public MessageBuilder(double latitude, double longitude) {
            messageName = MESSAGE_NAME;
            messageBody = new JSONObject();
            try {
                messageBody.put("latitude", latitude);
                messageBody.put("longitude", longitude);
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public CurrentLocationMessageHandler build() {
            return new CurrentLocationMessageHandler(this);
        }
    }

}
