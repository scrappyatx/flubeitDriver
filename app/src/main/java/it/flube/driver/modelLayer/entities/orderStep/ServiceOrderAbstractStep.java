/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.orderStep;

import it.flube.libbatchdata.entities.Timestamp;

/**
 * Created on 8/8/2017
 * Project : Driver
 */

public abstract class ServiceOrderAbstractStep {

    public enum TaskType {
        NAVIGATION,
        TAKE_PHOTOS,
        WAIT_FOR_EXTERNAL_TRIGGER,
        WAIT_FOR_USER_TRIGGER,
        AUTHORIZE_PAYMENT,
        GIVE_ASSET,
        RECEIVE_ASSET,
        WAIT_FOR_SERVICE_ON_ASSET
    }

    public enum WorkTiming {
        ON_TIME,
        LATE,
        VERY_LATE,
        NOT_APPLICABLE
    }

    public enum WorkStatus {
        NORMAL,
        CUSTOMER_SUPPORT,
        NOT_APPLICABLE
    }

    public enum WorkStage {
        NOT_STARTED,
        BEING_WORKED,
        COMPLETED,
        NOT_APPLICABLE
    }

    private String guid;
    private TaskType taskType;
    private WorkTiming workTiming;
    private WorkStatus workStatus;
    private WorkStage workStage;
    private String title;
    private String description;
    private String note;
    private String milestoneWhenFinished;
    private Timestamp startTime;
    private Timestamp finishTime;

    public ServiceOrderAbstractStep(){}

    public void setGuid(String guid){
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
       return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setNote(String note){
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    public TaskType getTaskType() {
        return taskType;
    }

    public void setWorkTiming(WorkTiming workTiming) {
        this.workTiming = workTiming;
    }

    public WorkTiming getWorkTiming(){
        return workTiming;
    }

    public void setWorkStatus(WorkStatus workStatus){
        this.workStatus = workStatus;
    }

    public WorkStatus getWorkStatus(){
        return workStatus;
    }

    public void setWorkStage(WorkStage workStage){
        this.workStage = workStage;
    }

    public WorkStage getWorkStage(){
        return workStage;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getStartTime(){
        return startTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public Timestamp getFinishTime(){
        return finishTime;
    }

    public void setMilestoneWhenFinished(String milestoneWhenFinished){
        this.milestoneWhenFinished = milestoneWhenFinished;
    }

    public String getMilestoneWhenFinished(){
        return milestoneWhenFinished;
    }

}
