/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.ChatMessage;

/**
 * Created on 9/3/2017
 * Project : Driver
 */

public class ChatMessageBuilder {
    private ChatMessage chatMessage;

    private ChatMessageBuilder(@NonNull Builder builder){
        this.chatMessage = builder.chatMessage;
    }

    private ChatMessage getChatMessage(){
        return chatMessage;
    }

    public static class Builder {
        private ChatMessage chatMessage;

        public Builder(){
            chatMessage = new ChatMessage();
            chatMessage.setGUID(UUID.randomUUID().toString());
        }

        public Builder senderDisplayName(@NonNull String senderDisplayName){
            this.chatMessage.setSenderDisplayName(senderDisplayName);
            return this;
        }

        public Builder message(@NonNull String message){
            this.chatMessage.setMessage(message);
            return this;
        }

        public Builder timestamp(@NonNull Date timestamp){
            this.chatMessage.setTimestamp(timestamp);
            return this;
        }

        private void validate(@NonNull ChatMessage chatMessage){
            // required PRESENT (must not be null)
            if (chatMessage.getGUID() == null){
                throw new IllegalStateException("GUID is null");
            }
            if (chatMessage.getTimestamp() == null){
                throw new IllegalStateException("timestamp is null");
            }
            if (chatMessage.getSenderDisplayName() == null) {
                throw new IllegalStateException("senderDisplayName is null");
            }
        }

        public ChatMessage build(){
            ChatMessage chatMessage = new ChatMessageBuilder(this).getChatMessage();
            validate(chatMessage);
            return chatMessage;
        }
    }
}
