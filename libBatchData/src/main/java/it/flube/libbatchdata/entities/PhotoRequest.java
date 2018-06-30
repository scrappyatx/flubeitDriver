/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class PhotoRequest {

    public enum PhotoStatus {
        PHOTO_SUCCESS,
        FAILED_ATTEMPTS,
        NO_ATTEMPTS
    }

    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;
    private String stepGuid;
    private Integer sequence;

    private String title;
    private String description;
    private PhotoStatus status;
    private Map<String, String> statusIconText;

    private Integer attemptCount;

    private Boolean hasPhotoHint;
    private String photoHintUrl;

    private Boolean hasNoAttemptImage;
    private String noAttemptImageUrl;

    private Boolean hasDeviceFile;
    private String deviceAbsoluteFileName;

    private Boolean hasCloudFile;
    private String cloudStorageFileName;
    private String cloudStorageDownloadUrl;

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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public PhotoStatus getStatus(){
        return status;
    }

    public void setStatus(PhotoStatus status){
        this.status = status;
    }

    public Map<String, String> getStatusIconText() {
        return statusIconText;
    }

    public void setStatusIconText(Map<String, String> statusIconText) {
        this.statusIconText = statusIconText;
    }

    public Integer getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(Integer attemptCount) {
        this.attemptCount = attemptCount;
    }

    public Boolean getHasPhotoHint() {
        return hasPhotoHint;
    }

    public void setHasPhotoHint(Boolean hasPhotoHint) {
        this.hasPhotoHint = hasPhotoHint;
    }

    public String getPhotoHintUrl() {
        return photoHintUrl;
    }

    public void setPhotoHintUrl(String photoHintUrl) {
        this.photoHintUrl = photoHintUrl;
    }

    public Boolean getHasNoAttemptImage() {
        return hasNoAttemptImage;
    }

    public void setHasNoAttemptImage(Boolean hasNoAttemptImage) {
        this.hasNoAttemptImage = hasNoAttemptImage;
    }

    public String getNoAttemptImageUrl() {
        return noAttemptImageUrl;
    }

    public void setNoAttemptImageUrl(String noAttemptImageUrl) {
        this.noAttemptImageUrl = noAttemptImageUrl;
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
}
