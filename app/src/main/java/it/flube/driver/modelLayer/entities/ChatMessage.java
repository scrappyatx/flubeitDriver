/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

import java.util.Date;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ChatMessage {
    private String guid;
    private String senderDisplayName;
    private String message;
    private Date timestamp;

    public String getGUID() {
        return guid;
    }

    public void setGUID(String guid) {
        this.guid = guid;
    }

    public String getSenderDisplayName() {
        return senderDisplayName;
    }

    public void setSenderDisplayName(String senderDisplayName) {
        this.senderDisplayName = senderDisplayName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
