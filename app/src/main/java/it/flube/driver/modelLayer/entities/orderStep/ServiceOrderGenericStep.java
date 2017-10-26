/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.orderStep;

import it.flube.driver.modelLayer.entities.Timestamp;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 10/25/2017
 * Project : Driver
 */

public class ServiceOrderGenericStep implements OrderStepInterface {

    private TaskType taskType;
    private String stepClassName;
    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;
    private Integer sequence;

    private OrderStepInterface.WorkTiming workTiming;
    private OrderStepInterface.WorkStatus workStatus;
    private OrderStepInterface.WorkStage workStage;

    private String title;
    private String description;
    private String note;
    private String milestoneWhenFinished;
    private Timestamp startTime;
    private Timestamp finishTime;

    public void setTaskType(TaskType taskType){
        this.taskType = taskType;
    }
    public TaskType getTaskType(){
        return taskType;
    }

    public void setStepClassName(String stepClassName){
        this.stepClassName = stepClassName;
    }

    public String getStepClassName(){
        return stepClassName;
    }

    @Override
    public String getGuid() {
        return guid;
    }

    @Override
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String getBatchGuid() {
        return batchGuid;
    }

    @Override
    public void setBatchGuid(String batchGuid) {
        this.batchGuid = batchGuid;
    }

    @Override
    public String getBatchDetailGuid() {
        return batchDetailGuid;
    }

    @Override
    public void setBatchDetailGuid(String batchDetailGuid) {
        this.batchDetailGuid = batchDetailGuid;
    }

    @Override
    public String getServiceOrderGuid() {
        return serviceOrderGuid;
    }

    @Override
    public void setServiceOrderGuid(String serviceOrderGuid) {
        this.serviceOrderGuid = serviceOrderGuid;
    }

    @Override
    public Integer getSequence() {
        return sequence;
    }

    @Override
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Override
    public WorkTiming getWorkTiming() {
        return workTiming;
    }

    @Override
    public void setWorkTiming(WorkTiming workTiming) {
        this.workTiming = workTiming;
    }

    @Override
    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    @Override
    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    @Override
    public WorkStage getWorkStage() {
        return workStage;
    }

    @Override
    public void setWorkStage(WorkStage workStage) {
        this.workStage = workStage;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String getMilestoneWhenFinished() {
        return milestoneWhenFinished;
    }

    @Override
    public void setMilestoneWhenFinished(String milestoneWhenFinished) {
        this.milestoneWhenFinished = milestoneWhenFinished;
    }

    @Override
    public Timestamp getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public Timestamp getFinishTime() {
        return finishTime;
    }

    @Override
    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }
}
