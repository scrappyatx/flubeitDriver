/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.serviceOrder;

import java.util.Map;

import it.flube.libbatchdata.entities.Timestamp;
import it.flube.libbatchdata.entities.orderStep.StepId;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 9/24/2017
 * Project : Driver
 */

public class ServiceOrderScaffold {

    private String guid;
    private String batchGuid;
    private String batchDetailGuid;

    private String title;
    private String description;

    private ServiceOrder.ServiceOrderStatus status;

    private Timestamp startTime;
    private Timestamp finishTime;

    private Map<String, StepId> stepIds;
    private Map<String, OrderStepInterface> steps;

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

    public ServiceOrder.ServiceOrderStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceOrder.ServiceOrderStatus status) {
        this.status = status;
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

    public Map<String, StepId> getStepIds() {
        return stepIds;
    }

    public void setStepIds(Map<String, StepId> stepIds) {
        this.stepIds = stepIds;
    }

    public Map<String, OrderStepInterface> getSteps() {
        return steps;
    }

    public void setSteps(Map<String, OrderStepInterface> steps) {
        this.steps = steps;
    }
}
