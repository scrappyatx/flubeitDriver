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

public class CurrentOffersRequestMessageHandler implements AblyChannel.MessageSend  {

    private final static String TAG = "CurrentOffersRequestMessageHandler";
    private final static String MESSAGE_NAME = "requestCurrentOffers";

    private final String messageName;
    private final JSONObject messageBody;

    private CurrentOffersRequestMessageHandler(MessageBuilder builder) {
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

        public MessageBuilder() {
            messageName = "requestCurrentOffers";
            messageBody = new JSONObject();

            //TODO define a message body for requestCurrentOffers
            try {
                messageBody.put("filler", "filler");
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public CurrentOffersRequestMessageHandler build() {
            return new CurrentOffersRequestMessageHandler(this);
        }
    }

}
