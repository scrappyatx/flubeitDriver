/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 8/11/2018
 * Project : Driver
 */
public class ReceiptRequest {
    public enum ReceiptStatus {
        COMPLETED_SUCCESS,
        COMPLETED_FAILED,
        NOT_ATTEMPTED
    }

    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;
    private String stepGuid;

    private Boolean hasDeviceFile;
    private String deviceAbsoluteFileName;

    private Integer attemptCount;
    private Boolean hasCloudFile;
    private String cloudStorageFileName;
    private String cloudStorageDownloadUrl;

    private ReceiptStatus receiptStatus;
    private Map<String, String> statusIconText;

    private Boolean shareWithCustomer;

    private ReceiptAnalysis receiptAnalysis;

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

    public String getBatchDetailGuid() {
        return batchDetailGuid;
    }

    public void setBatchDetailGuid(String batchDetailGuid) {
        this.batchDetailGuid = batchDetailGuid;
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

    public Boolean getHasDeviceFile() {
        return hasDeviceFile;
    }

    public void setHasDeviceFile(Boolean hasDeviceFile) {
        this.hasDeviceFile = hasDeviceFile;
    }

    public String getDeviceAbsoluteFileName() {
        return deviceAbsoluteFileName;
    }

    public void setDeviceAbsoluteFileName(String deviceAbsoluteFileName) {
        this.deviceAbsoluteFileName = deviceAbsoluteFileName;
    }

    public Integer getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Boolean getHasCloudFile() {
        return hasCloudFile;
    }

    public void setHasCloudFile(Boolean hasCloudFile) {
        this.hasCloudFile = hasCloudFile;
    }

    public String getCloudStorageFileName() {
        return cloudStorageFileName;
    }

    public void setCloudStorageFileName(String cloudStorageFileName) {
        this.cloudStorageFileName = cloudStorageFileName;
    }

    public String getCloudStorageDownloadUrl() {
        return cloudStorageDownloadUrl;
    }

    public void setCloudStorageDownloadUrl(String cloudStorageDownloadUrl) {
        this.cloudStorageDownloadUrl = cloudStorageDownloadUrl;
    }

    public ReceiptStatus getReceiptStatus() {
        return receiptStatus;
    }

    public void setReceiptStatus(ReceiptStatus receiptStatus) {
        this.receiptStatus = receiptStatus;
    }

    public Map<String, String> getStatusIconText() {
        return statusIconText;
    }

    public void setStatusIconText(Map<String, String> statusIconText) {
        this.statusIconText = statusIconText;
    }

    public Boolean getShareWithCustomer() {
        return shareWithCustomer;
    }

    public void setShareWithCustomer(Boolean shareWithCustomer) {
        this.shareWithCustomer = shareWithCustomer;
    }

    public ReceiptAnalysis getReceiptAnalysis() {
        return receiptAnalysis;
    }

    public void setReceiptAnalysis(ReceiptAnalysis receiptAnalysis) {
        this.receiptAnalysis = receiptAnalysis;
    }
}
