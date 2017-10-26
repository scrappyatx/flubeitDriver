/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities;

/**
 * Created on 9/17/2017
 * Project : Driver
 */

public class FileAttachment {
    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;
    private String stepGuid;
    private String photoRequestGuid;
    private String chatHistoryGuid;
    private String chatMessageGuid;

    private String fileName;
    private String filePath;
    private String contentType;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getServiceOrderGuid() {
        return serviceOrderGuid;
    }

    public void setServiceOrderGuid(String serviceOrderGuid) {
        this.serviceOrderGuid = serviceOrderGuid;
    }

    public String getBatchGuid() {
        return batchGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public String getBatchDetailGuid() {
        return batchDetailGuid;
    }

    public void setBatchDetailGuid(String batchDetailGuid) {
        this.batchDetailGuid = batchDetailGuid;
    }

    public String getStepGuid() {
        return stepGuid;
    }

    public void setStepGuid(String stepGuid) {
        this.stepGuid = stepGuid;
    }

    public String getPhotoRequestGuid() {
        return photoRequestGuid;
    }

    public void setPhotoRequestGuid(String photoRequestGuid) {
        this.photoRequestGuid = photoRequestGuid;
    }

    public String getChatHistoryGuid() {
        return chatHistoryGuid;
    }

    public void setChatHistoryGuid(String chatHistoryGuid) {
        this.chatHistoryGuid = chatHistoryGuid;
    }

    public String getChatMessageGuid() {
        return chatMessageGuid;
    }

    public void setChatMessageGuid(String chatMessageGuid) {
        this.chatMessageGuid = chatMessageGuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
