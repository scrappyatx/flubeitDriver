/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.serviceOrder;

import java.util.UUID;

import it.flube.driver.modelLayer.entities.Timestamp;

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

    protected ServiceOrderAbstractStep(){}

    protected void setGUID(String guid){
        this.guid = guid;
    }

    protected String getGUID() {
        return guid;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected String getTitle() {
       return title;
    }

    protected void setDescription(String description) {
        this.description = description;
    }
    protected String getDescription() {
        return description;
    }

    protected void setNote(String note){
        this.note = note;
    }

    protected String getNote() {
        return note;
    }

    protected void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
    protected TaskType getTaskType() {
        return taskType;
    }

    protected void setWorkTiming(WorkTiming workTiming) {
        this.workTiming = workTiming;
    }

    protected WorkTiming getWorkTiming(){
        return workTiming;
    }

    protected void setWorkStatus(WorkStatus workStatus){
        this.workStatus = workStatus;
    }

    protected WorkStatus getWorkStatus(){
        return workStatus;
    }

    protected void setWorkStage(WorkStage workStage){
        this.workStage = workStage;
    }

    protected WorkStage getWorkStage(){
        return workStage;
    }

    protected void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    protected Timestamp getStartTime(){
        return startTime;
    }

    protected void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    protected Timestamp getFinishTime(){
        return finishTime;
    }

    protected void setMilestoneWhenFinished(String milestoneWhenFinished){
        this.milestoneWhenFinished = milestoneWhenFinished;
    }

    protected String getMilestoneWhenFinished(){
        return milestoneWhenFinished;
    }

}
