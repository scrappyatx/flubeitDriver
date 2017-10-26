/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.ChatHistory;

/**
 * Created on 9/25/2017
 * Project : Driver
 */

public class ChatHistoryBuilder {
    private ChatHistory chatHistory;

    private ChatHistoryBuilder(Builder builder){
        this.chatHistory = builder.chatHistory;
    }

    private ChatHistory getChatHistory(){
        return chatHistory;
    }

    public static class Builder {
        private ChatHistory chatHistory;

        public Builder(){
            this.chatHistory = new ChatHistory();
            this.chatHistory.setGuid(BuilderUtilities.generateGuid());
        }

        public Builder guid(String guid){
            this.chatHistory.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.chatHistory.setBatchGuid(guid);
            return this;
        }

        public Builder serviceOrderGuid(String guid){
            this.chatHistory.setServiceOrderGuid(guid);
            return this;
        }

        public Builder chatType(ChatHistory.ChatType chatType) {
            this.chatHistory.setChatType(chatType);
            return this;
        }


        private void validate(ChatHistory chatHistory){

        }

        public ChatHistory build(){
            ChatHistory chatHistory = new ChatHistoryBuilder(this).getChatHistory();
            validate(chatHistory);
            return chatHistory;
        }
    }
}
