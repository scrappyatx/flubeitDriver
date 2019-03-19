/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;



import java.util.Date;

import it.flube.libbatchdata.entities.ChatMessage;
import it.flube.libbatchdata.utilities.BuilderUtilities;

/**
 * Created on 9/3/2017
 * Project : Driver
 */

public class ChatMessageBuilder {
    private ChatMessage chatMessage;

    private ChatMessageBuilder(Builder builder){
        this.chatMessage = builder.chatMessage;
    }

    private ChatMessage getChatMessage(){
        return chatMessage;
    }

    public static class Builder {
        private ChatMessage chatMessage;

        public Builder(){
            this.chatMessage = new ChatMessage();
            this.chatMessage.setGuid(BuilderUtilities.generateGuid());
        }

        public Builder guid(String guid){
            this.chatMessage.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.chatMessage.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.chatMessage.setBatchDetailGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.chatMessage.setServiceOrderGuid(guid);
            return this;
        }

        public Builder chatHistoryGuid(String guid){
            this.chatMessage.setChatHistoryGuid(guid);
            return this;
        }

        public Builder senderDisplayName(String senderDisplayName){
            this.chatMessage.setSenderDisplayName(senderDisplayName);
            return this;
        }

        public Builder senderRole(ChatMessage.SenderRole senderRole){
            this.chatMessage.setSenderRole(senderRole);
            return this;
        }

        public Builder message(String message){
            this.chatMessage.setMessage(message);
            return this;
        }

        public Builder sendTime(Date sendTime){
            this.chatMessage.setSendTime(sendTime);
            return this;
        }

        private void validate(ChatMessage chatMessage){
            // required PRESENT (must not be null)
            if (chatMessage.getGuid() == null){
                throw new IllegalStateException("chatmessage guid is null");
            }
            if (chatMessage.getSendTime() == null){
                throw new IllegalStateException("chatmessage timestamp is null");
            }
            if (chatMessage.getSenderDisplayName() == null) {
                throw new IllegalStateException("chatmessage senderDisplayName is null");
            }
        }

        public ChatMessage build(){
            ChatMessage chatMessage = new ChatMessageBuilder(this).getChatMessage();
            validate(chatMessage);
            return chatMessage;
        }
    }
}
