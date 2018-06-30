/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.batch;

import java.util.ArrayList;
import java.util.Date;

import it.flube.libbatchdata.entities.DisplayDistance;
import it.flube.libbatchdata.entities.DisplayTiming;
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

    private WorkStatus workStatus;

    private Integer earliestStartMinutesPrior;
    private Integer latestStartMinutesAfter;

    private Date expectedStartTime;
    private Date expectedFinishTime;
    private Date offerExpiryTime;

    private Object actualFinishTime;
    private Object actualStartTime;

    private DisplayDistance displayDistance;
    private PotentialEarnings potentialEarnings;

    private Integer serviceOrderCount;
    private Integer routeStopCount;

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

    public Date getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(Date expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public Date getExpectedFinishTime() {
        return expectedFinishTime;
    }

    public void setExpectedFinishTime(Date expectedFinishTime) {
        this.expectedFinishTime = expectedFinishTime;
    }

    public Object getActualFinishTime() {
        return actualFinishTime;
    }

    public void setActualFinishTime(Object actualFinishTime) {
        this.actualFinishTime = actualFinishTime;
    }

    public Object getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Object actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getOfferExpiryTime() {
        return offerExpiryTime;
    }

    public void setOfferExpiryTime(Date offerExpiryTime) {
        this.offerExpiryTime = offerExpiryTime;
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
}
