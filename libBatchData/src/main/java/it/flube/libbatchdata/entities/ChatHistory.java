/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.ArrayList;

/**
 * Created on 9/17/2017
 * Project : Driver
 */

public class ChatHistory {

    public enum ChatType {
        DRIVER_CHAT_HISTORY,
        CUSTOMER_CHAT_HISTORY,
        SERVICE_PROVIDER_CHAT_HISTORY
    }

    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;

    private ChatType chatType;

    private ArrayList<String> chatMessageGuids;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public String getBatchGuid() {
        return batchGuid;
    }

    public String getBatchDetailGuid() {
        return batchDetailGuid;
    }

    public void setBatchDetailGuid(String batchDetailGuid) {
        this.batchDetailGuid = batchDetailGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public String getServiceOrderGuid() {
        return serviceOrderGuid;
    }

    public void setServiceOrderGuid(String serviceOrderGuid) {
        this.serviceOrderGuid = serviceOrderGuid;
    }

    public ArrayList<String> getChatMessageGuids() {
        return chatMessageGuids;
    }

    public void setChatMessageGuids(ArrayList<String> chatMessageGuids) {
        this.chatMessageGuids = chatMessageGuids;
    }
}
