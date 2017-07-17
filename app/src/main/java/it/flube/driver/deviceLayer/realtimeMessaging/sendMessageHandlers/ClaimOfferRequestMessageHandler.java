/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.realtimeMessaging.sendMessageHandlers;

import org.json.JSONException;
import org.json.JSONObject;

import it.flube.driver.deviceLayer.realtimeMessaging.AblyChannel;
import it.flube.driver.deviceLayer.realtimeMessaging.AblyMessageSend;
import timber.log.Timber;

/**
 * Created on 7/15/2017
 * Project : Driver
 */

public class ClaimOfferRequestMessageHandler implements AblyChannel.MessageSend {

    private final static String TAG = "CurrentOffersRequestMessageHandler";
    private final static String MESSAGE_NAME = "claimOfferRequest";

    private final String messageName;
    private final JSONObject messageBody;

    private ClaimOfferRequestMessageHandler(MessageBuilder builder) {
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
        private String messageName;
        private JSONObject messageBody;

        public MessageBuilder(String offerOID){
            messageName = MESSAGE_NAME;
            messageBody = new JSONObject();
            try {
                messageBody.put("offerOID", offerOID);
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public ClaimOfferRequestMessageHandler build() {
            return new ClaimOfferRequestMessageHandler(this);
        }

    }

}
