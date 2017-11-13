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

public class MilestoneEventMessageHandler implements
        AblyChannel.MessageSend {

    private final static String TAG = "MilestoneEventMessageHandler";
    private final static String MESSAGE_NAME = "milestoneEvent";

    private final String messageName;
    private final JSONObject messageBody;

    private MilestoneEventMessageHandler(MilestoneEventMessageHandler.MessageBuilder builder) {
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

        public MessageBuilder(String milestoneEvent) {
            messageName = MESSAGE_NAME;
            messageBody = new JSONObject();
            try {
                messageBody.put("milestone", milestoneEvent);
            } catch (JSONException e) {
                Timber.tag(TAG).e(e);
            }
        }

        public MilestoneEventMessageHandler build() {
            return new MilestoneEventMessageHandler(this);
        }
    }
}
