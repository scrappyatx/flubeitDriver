/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.batch;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.DisplayTiming;
import it.flube.driver.modelLayer.entities.Earnings;
import it.flube.driver.modelLayer.entities.PotentialEarnings;
import it.flube.driver.modelLayer.entities.Timestamp;
import it.flube.driver.modelLayer.entities.asset.Vehicle;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;

/**
 * Created on 3/22/2017
 * Package : ${PACKAGE_NAME}
 * Project : Driver
 */

public class Batch {
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

    public enum BatchType {
        PRODUCTION,
        PRODUCTION_TEST,
        MOBILE_DEMO
    }

    private ClaimStatus claimStatus;
    private WorkStatus workStatus;
    private BatchType type;

    private String guid;
    private String title;
    private String description;

    private String assignedClientId;

    private Timestamp startTime;
    private Timestamp finishTime;

    private DisplayTiming displayTiming;
    private PotentialEarnings potentialEarnings;

    private ArrayList<ServiceOrder> serviceOrderList;
    private Integer serviceOrderIndex;

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    public ClaimStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }

    public BatchType getType() {
        return type;
    }

    public void setType(BatchType type) {
        this.type = type;
    }

    public void setGUID(String guid) {
        this.guid = guid;
    }

    public String getGUID() {
        return guid;
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

    public String getAssignedClientId() {
        return assignedClientId;
    }

    public void setAssignedClientId(String assignedClientId) {
        this.assignedClientId = assignedClientId;
    }

    public void setDisplayTiming(DisplayTiming displayTiming) {
        this.displayTiming = displayTiming;
    }

    public DisplayTiming getDisplayTiming() {
        return displayTiming;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public PotentialEarnings getPotentialEarnings() {
        return potentialEarnings;
    }

    public void setPotentialEarnings(PotentialEarnings potentialEarnings) {
        this.potentialEarnings = potentialEarnings;
    }

    public ArrayList<ServiceOrder> getServiceOrderList() {
        return serviceOrderList;
    }

    public void setServiceOrderList(ArrayList<ServiceOrder> serviceOrderList) {
        this.serviceOrderList = serviceOrderList;
    }

    public Integer getServiceOrderIndex() {
        return serviceOrderIndex;
    }

    public void setServiceOrderIndex(Integer serviceOrderIndex) {
        this.serviceOrderIndex = serviceOrderIndex;
    }
}
