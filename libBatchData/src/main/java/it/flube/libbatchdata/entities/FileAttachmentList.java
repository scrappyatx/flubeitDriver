/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.ArrayList;

/**
 * Created on 9/17/2017
 * Project : Driver
 */

public class FileAttachmentList {
    private String guid;
    private String batchGuid;
    private String batchDetailGuids;
    private String serviceOrderGuid;
    private String stepGuid;
    private String photoRequestGuid;
    private String chatHistoryGuid;
    private String chatMessageGuid;

    private ArrayList<String> fileAttachmentGuids;


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getBatchGuid() {
        return batchGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public String getBatchDetailGuids() {
        return batchDetailGuids;
    }

    public void setBatchDetailGuids(String batchDetailGuids) {
        this.batchDetailGuids = batchDetailGuids;
    }

    public String getServiceOrderGuid() {
        return serviceOrderGuid;
    }

    public void setServiceOrderGuid(String serviceOrderGuid) {
        this.serviceOrderGuid = serviceOrderGuid;
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

    public ArrayList<String> getFileAttachmentGuids() {
        return fileAttachmentGuids;
    }

    public void setFileAttachmentGuids(ArrayList<String> fileAttachmentGuids) {
        this.fileAttachmentGuids = fileAttachmentGuids;
    }
}
