/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.libbatchdata.interfaces.FileUploadInterface;

/**
 * Created on 1/21/2019
 * Project : Driver
 */
public class FileToUploadInfo {


    //owner info
    private String clientId;
    private String batchGuid;
    private String serviceOrderGuid;
    private String orderStepGuid;
    private String ownerGuid;
    private FileUploadInterface.OwnerType ownerType;

    //device info
    private String deviceGuid;
    private String deviceAbsoluteFileName;

    //filename info
    private String cloudFileName;

    // progress info
    private String sessionUriString;
    private String progress;

    //attempt info
    private Integer attempts;

    //upload complete info
    private String cloudDownloadUrl;
    private Boolean uploadSuccess;
    private long bytesTransferred;
    private long fileSizeBytes;
    private String contentType;


    //deletion key
    private String deletionKey;


    //required empty constructor
    public FileToUploadInfo(){

    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBatchGuid() {
        return batchGuid;
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

    public String getOrderStepGuid() {
        return orderStepGuid;
    }

    public void setOrderStepGuid(String orderStepGuid) {
        this.orderStepGuid = orderStepGuid;
    }

    public String getOwnerGuid() {
        return ownerGuid;
    }

    public void setOwnerGuid(String ownerGuid) {
        this.ownerGuid = ownerGuid;
    }

    public FileUploadInterface.OwnerType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(FileUploadInterface.OwnerType ownerType) {
        this.ownerType = ownerType;
    }

    public String getDeviceGuid() {
        return deviceGuid;
    }

    public void setDeviceGuid(String deviceGuid) {
        this.deviceGuid = deviceGuid;
    }

    public String getDeviceAbsoluteFileName() {
        return deviceAbsoluteFileName;
    }

    public void setDeviceAbsoluteFileName(String deviceAbsoluteFileName) {
        this.deviceAbsoluteFileName = deviceAbsoluteFileName;
    }

    public String getCloudFileName() {
        return cloudFileName;
    }

    public void setCloudFileName(String cloudFileName) {
        this.cloudFileName = cloudFileName;
    }

    public String getSessionUriString() {
        return sessionUriString;
    }

    public void setSessionUriString(String sessionUriString) {
        this.sessionUriString = sessionUriString;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String getCloudDownloadUrl() {
        return cloudDownloadUrl;
    }

    public void setCloudDownloadUrl(String cloudDownloadUrl) {
        this.cloudDownloadUrl = cloudDownloadUrl;
    }

    public Boolean getUploadSuccess() {
        return uploadSuccess;
    }

    public void setUploadSuccess(Boolean uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
    }

    public String getDeletionKey() {
        return deletionKey;
    }

    public void setDeletionKey(String deletionKey) {
        this.deletionKey = deletionKey;
    }

    public long getBytesTransferred() {
        return bytesTransferred;
    }

    public void setBytesTransferred(long bytesTransferred) {
        this.bytesTransferred = bytesTransferred;
    }

    public long getFileSizeBytes() {
        return fileSizeBytes;
    }

    public void setFileSizeBytes(long fileSizeBytes) {
        this.fileSizeBytes = fileSizeBytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
