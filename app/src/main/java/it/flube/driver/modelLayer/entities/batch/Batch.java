/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.batch;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Earnings;
import it.flube.driver.modelLayer.entities.Timestamp;
import it.flube.driver.modelLayer.entities.asset.Vehicle;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;

/**
 * Created on 3/22/2017
 * Package : ${PACKAGE_NAME}
 * Project : Driver
 */

public class Batch {
    public enum BatchStatus {
        NOT_STARTED,
        ACTIVE,
        COMPLETED_SUCCESS,
        COMPLETED_PROBLEM,
    }

    public enum BatchType {
        PRODUCTION,
        PRODUCTION_TEST,
        MOBILE_DEMO
    }

    private BatchStatus batchStatus;
    private BatchType batchType;

    private String batchGUID;
    private String batchTitle;
    private String batchDescription;

    private Timestamp batchStartTime;
    private Timestamp batchEndTime;

    private Earnings baseEarnings;
    private Earnings extraEarnings;

    private ArrayList<ServiceOrder> serviceOrderList;
    private Integer serviceOrderIndex;

    public BatchStatus getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(BatchStatus batchStatus) {
        this.batchStatus = batchStatus;
    }

    public BatchType getBatchType() {
        return batchType;
    }

    public void setBatchType(BatchType batchType) {
        this.batchType = batchType;
    }

    public void setBatchGUID(String batchGUID) {
        this.batchGUID = batchGUID;
    }

    public String getBatchGUID() {
        return batchGUID;
    }

    public String getBatchTitle() {
        return batchTitle;
    }

    public void setBatchTitle(String batchTitle) {
        this.batchTitle = batchTitle;
    }

    public String getBatchDescription() {
        return batchDescription;
    }

    public void setBatchDescription(String batchDescription) {
        this.batchDescription = batchDescription;
    }

    public Timestamp getBatchStartTime() {
        return batchStartTime;
    }

    public void setBatchStartTime(Timestamp batchStartTime) {
        this.batchStartTime = batchStartTime;
    }

    public Timestamp getBatchEndTime() {
        return batchEndTime;
    }

    public void setBatchEndTime(Timestamp batchEndTime) {
        this.batchEndTime = batchEndTime;
    }

    public Earnings getBaseEarnings() {
        return baseEarnings;
    }

    public void setBaseEarnings(Earnings baseEarnings) {
        this.baseEarnings = baseEarnings;
    }

    public Earnings getExtraEarnings() {
        return extraEarnings;
    }

    public void setExtraEarnings(Earnings extraEarnings) {
        this.extraEarnings = extraEarnings;
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
