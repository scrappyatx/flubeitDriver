/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import java.util.Map;

import it.flube.libbatchdata.entities.Timestamp;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public abstract class AbstractStep implements OrderStepInterface {
    protected OrderStepInterface.TaskType taskType;
    protected String taskTypeIconText;

    protected String guid;
    protected String batchGuid;
    protected String batchDetailGuid;
    protected String serviceOrderGuid;
    protected Integer sequence;

    protected OrderStepInterface.WorkTiming workTiming;
    protected Map<String, String> workTimingIconTextMap;

    protected OrderStepInterface.WorkStatus workStatus;
    protected Map<String, String> workStatusIconTextMap;

    protected OrderStepInterface.WorkStage workStage;
    protected Map<String, String> workStageIconTextMap;

    protected String title;
    protected String description;
    protected String note;
    protected String milestoneWhenFinished;

    protected Integer durationMinutes;
    protected Timestamp startTime;
    protected Timestamp finishTime;



    public TaskType getTaskType(){
        return taskType;
    }
    public void setTaskType(TaskType taskType){
        this.taskType = taskType;
    }

    public String getTaskTypeIconText() {
        return taskTypeIconText;
    }

    public void setTaskTypeIconText(String taskTypeIconText) {
        this.taskTypeIconText = taskTypeIconText;
    }


    public Map<String, String> getWorkTimingIconTextMap() {
        return workTimingIconTextMap;
    }


    public void setWorkTimingIconTextMap(Map<String, String> workTimingIconTextMap) {
        this.workTimingIconTextMap = workTimingIconTextMap;
    }


    public Map<String, String> getWorkStatusIconTextMap() {
        return workStatusIconTextMap;
    }


    public void setWorkStatusIconTextMap(Map<String, String> workStatusIconTextMap) {
        this.workStatusIconTextMap = workStatusIconTextMap;
    }


    public Map<String, String> getWorkStageIconTextMap() {
        return workStageIconTextMap;
    }


    public void setWorkStageIconTextMap(Map<String, String> workStageIconTextMap) {
        this.workStageIconTextMap = workStageIconTextMap;
    }


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


    public Integer getSequence() {
        return sequence;
    }


    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }


    public OrderStepInterface.WorkTiming getWorkTiming() {
        return workTiming;
    }


    public void setWorkTiming(OrderStepInterface.WorkTiming workTiming) {
        this.workTiming = workTiming;
    }


    public OrderStepInterface.WorkStatus getWorkStatus() {
        return workStatus;
    }


    public void setWorkStatus(OrderStepInterface.WorkStatus workStatus) {
        this.workStatus = workStatus;
    }


    public OrderStepInterface.WorkStage getWorkStage() {
        return workStage;
    }


    public void setWorkStage(OrderStepInterface.WorkStage workStage) {
        this.workStage = workStage;
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


    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }


    public String getMilestoneWhenFinished() {
        return milestoneWhenFinished;
    }


    public void setMilestoneWhenFinished(String milestoneWhenFinished) {
        this.milestoneWhenFinished = milestoneWhenFinished;
    }


    public void setDurationMinutes(Integer durationMinutes){
        this.durationMinutes = durationMinutes;
    }
    public Integer getDurationMinutes(){
        return durationMinutes;
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

}
