/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.batch;

import java.util.Map;

import it.flube.libbatchdata.entities.BatchNotificationSettings;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.Customer;
import it.flube.libbatchdata.entities.DriverInfo;
import it.flube.libbatchdata.entities.DisplayDistance;
import it.flube.libbatchdata.entities.FileUpload;
import it.flube.libbatchdata.entities.PotentialEarnings;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class BatchDetail {
    public enum BatchType {
        PRODUCTION,
        PRODUCTION_TEST,
        MOBILE_DEMO
    }

    public enum ClaimStatus {
        CLAIMED,
        NOT_CLAIMED
    }

    public enum WorkStatus {
        NOT_STARTED,
        ACTIVE,
        PAUSED,
        COMPLETED_SUCCESS,
        COMPLETED_PROBLEM,
    }

    private String guid;
    private String batchGuid;

    private String title;
    private String description;
    private String iconUrl;

    private BatchType batchType;
    private ClaimStatus claimStatus;
    private String claimedByClientId;
    private DriverInfo driverInfo;

    private WorkStatus workStatus;

    private Integer earliestStartMinutesPrior;
    private Integer latestStartMinutesAfter;

    private Long expectedStartTime;
    private Long expectedFinishTime;
    private Long offerExpiryTime;

    private Long actualFinishTime;
    private Long actualStartTime;

    private DisplayDistance displayDistance;
    private PotentialEarnings potentialEarnings;

    private Integer serviceOrderCount;
    private Integer routeStopCount;

    private Map<String, ContactPerson> contactPersons;
    private Map<String, Map<String, ContactPerson>> contactPersonsByServiceOrder;

    private Customer customer;

    private BatchNotificationSettings batchNotificationSettings;

    private Map<String, FileUpload> fileUploads;

    //// getters & setters

    public BatchType getBatchType() {
        return batchType;
    }

    public void setBatchType(BatchType batchType) {
        this.batchType = batchType;
    }

    public ClaimStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }

    public String getClaimedByClientId() {
        return claimedByClientId;
    }

    public void setClaimedByClientId(String claimedByClientId) {
        this.claimedByClientId = claimedByClientId;
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    public String getBatchGuid() {
        return batchGuid;
    }

    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public DisplayDistance getDisplayDistance() {
        return displayDistance;
    }

    public void setDisplayDistance(DisplayDistance displayDistance) {
        this.displayDistance = displayDistance;
    }

    public PotentialEarnings getPotentialEarnings() {
        return potentialEarnings;
    }

    public void setPotentialEarnings(PotentialEarnings potentialEarnings) {
        this.potentialEarnings = potentialEarnings;
    }

    public Integer getServiceOrderCount() {
        return serviceOrderCount;
    }

    public void setServiceOrderCount(Integer serviceOrderCount) {
        this.serviceOrderCount = serviceOrderCount;
    }

    public Integer getRouteStopCount() {
        return routeStopCount;
    }

    public void setRouteStopCount(Integer routeStopCount) {
        this.routeStopCount = routeStopCount;
    }

    public Long getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(Long expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public Long getExpectedFinishTime() {
        return expectedFinishTime;
    }

    public void setExpectedFinishTime(Long expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }

    public Long getOfferExpiryTime() {
        return offerExpiryTime;
    }

    public void setOfferExpiryTime(Long offerExpiryTime) {
        this.offerExpiryTime = offerExpiryTime;
    }

    public Long getActualFinishTime() {
        return actualFinishTime;
    }

    public void setActualFinishTime(Long actualFinishTime) {
        this.actualFinishTime = actualFinishTime;
    }

    public Long getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Long actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Integer getEarliestStartMinutesPrior() {
        return earliestStartMinutesPrior;
    }

    public void setEarliestStartMinutesPrior(Integer earliestStartMinutesPrior) {
        this.earliestStartMinutesPrior = earliestStartMinutesPrior;
    }

    public Integer getLatestStartMinutesAfter() {
        return latestStartMinutesAfter;
    }

    public void setLatestStartMinutesAfter(Integer latestStartMinutesAfter) {
        this.latestStartMinutesAfter = latestStartMinutesAfter;
    }

    public Map<String, ContactPerson> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(Map<String, ContactPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }

    public Map<String, Map<String, ContactPerson>> getContactPersonsByServiceOrder() {
        return contactPersonsByServiceOrder;
    }

    public void setContactPersonsByServiceOrder(Map<String, Map<String, ContactPerson>> contactPersonsByServiceOrder) {
        this.contactPersonsByServiceOrder = contactPersonsByServiceOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BatchNotificationSettings getBatchNotificationSettings() {
        return batchNotificationSettings;
    }

    public void setBatchNotificationSettings(BatchNotificationSettings batchNotificationSettings) {
        this.batchNotificationSettings = batchNotificationSettings;
    }

    public Map<String, FileUpload> getFileUploads() {
        return fileUploads;
    }

    public void setFileUploads(Map<String, FileUpload> fileUploads) {
        this.fileUploads = fileUploads;
    }
}
