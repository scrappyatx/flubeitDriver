/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.orderStep;

import java.util.HashMap;
import java.util.Map;

import it.flube.driver.modelLayer.entities.Destination;
import it.flube.driver.modelLayer.entities.Timestamp;
import it.flube.driver.modelLayer.entities.orderStep.ServiceOrderAbstractStep;
import it.flube.driver.modelLayer.interfaces.OrderStepInterface;

/**
 * Created on 8/8/2017
 * Project : Driver
 */

public class ServiceOrderNavigationStep implements OrderStepInterface {
    private static final OrderStepInterface.TaskType TASK_TYPE = TaskType.NAVIGATION;

    private String taskTypeIconText;

    private String guid;
    private String batchGuid;
    private String batchDetailGuid;
    private String serviceOrderGuid;
    private Integer sequence;

    private OrderStepInterface.WorkTiming workTiming;
    private Map<String, String> workTimingIconTextMap;

    private OrderStepInterface.WorkStatus workStatus;
    private Map<String, String> workStatusIconTextMap;

    private OrderStepInterface.WorkStage workStage;
    private Map<String, String> workStageIconTextMap;

    private String title;
    private String description;
    private String note;
    private String milestoneWhenFinished;
    private Timestamp startTime;
    private Timestamp finishTime;

    private double closeEnoughInFeet;
    private Boolean atDestination;
    private Destination destination;

    public TaskType getTaskType() {
        return TASK_TYPE;
    }

    public String getTaskTypeIconText() {
        return taskTypeIconText;
    }

    public void setTaskTypeIconText(String taskTypeIconText) {
        this.taskTypeIconText = taskTypeIconText;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getGuid() {
        return guid;
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

    public WorkTiming getWorkTiming() {
        return workTiming;
    }

    public void setWorkTiming(WorkTiming workTiming) {
        this.workTiming = workTiming;
    }


    public Map<String, String> getWorkTimingIconTextMap() {
        return workTimingIconTextMap;
    }

    public void setWorkTimingIconTextMap(Map<String, String> workTimingIconTextMap) {
        this.workTimingIconTextMap = workTimingIconTextMap;
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    public Map<String, String> getWorkStatusIconTextMap() {
        return workStatusIconTextMap;
    }

    public void setWorkStatusIconTextMap(Map<String, String> workStatusIconTextMap) {
        this.workStatusIconTextMap = workStatusIconTextMap;
    }

    public WorkStage getWorkStage() {
        return workStage;
    }

    public void setWorkStage(WorkStage workStage) {
        this.workStage = workStage;
    }

    public Map<String, String> getWorkStageIconTextMap() {
        return workStageIconTextMap;
    }

    public void setWorkStageIconTextMap(Map<String, String> workStageIconTextMap) {
       this.workStageIconTextMap = workStageIconTextMap;
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

    public double getCloseEnoughInFeet() {
        return closeEnoughInFeet;
    }

    public void setCloseEnoughInFeet(double closeEnoughInFeet) {
        this.closeEnoughInFeet = closeEnoughInFeet;
    }

    public Boolean getAtDestination() {
        return atDestination;
    }

    public void setAtDestination(Boolean atDestination) {
        this.atDestination = atDestination;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
