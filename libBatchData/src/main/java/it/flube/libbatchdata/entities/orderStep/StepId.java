/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 9/17/2017
 * Project : Driver
 */

public class StepId {

    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;

    private Integer sequence;
    private String stepGuid;
    private OrderStepInterface.TaskType taskType;
    private String stepClassName;


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

    public OrderStepInterface.TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(OrderStepInterface.TaskType taskType) {
        this.taskType = taskType;
    }

    public String getStepClassName() {
        return stepClassName;
    }

    public void setStepClassName(String stepClassName) {
        this.stepClassName = stepClassName;
    }
}
