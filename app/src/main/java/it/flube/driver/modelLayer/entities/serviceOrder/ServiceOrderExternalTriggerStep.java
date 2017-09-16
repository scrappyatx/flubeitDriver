/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.entities.serviceOrder;

import it.flube.driver.modelLayer.entities.Timestamp;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderExternalTriggerStep extends ServiceOrderAbstractStep {

    public enum TriggerMethod {
        EXTERNAL_TRIGGER_OCCURED,
        USER_TRIGGER_OCCURED,
        NO_TRIGGER_OCCURED,
    }

    public ServiceOrderExternalTriggerStep(){
        super.setTaskType(TaskType.WAIT_FOR_EXTERNAL_TRIGGER);
    }

    private TriggerMethod howTriggered;

    public void setHowTriggered(TriggerMethod howTriggered) {
        this.howTriggered = howTriggered;
    }

    public TriggerMethod getHowTriggered(){
        return howTriggered;
    }

    /// setters / getters for instance variables from abstract Service Order class
    @Override
    public void setTitle(String stepTitle) {
        super.setTitle(stepTitle);
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public void setDescription(String stepDescription) {
        super.setDescription(stepDescription);
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void setNote(String stepNote){
        super.setNote(stepNote);
    }

    @Override
    public String getNote() {
        return super.getNote();
    }

    @Override
    public TaskType getTaskType() {
        return super.getTaskType();
    }

    @Override
    public void setWorkTiming(WorkTiming workTiming) {
        super.setWorkTiming(workTiming);
    }

    @Override
    public WorkTiming getWorkTiming(){
        return super.getWorkTiming();
    }

    @Override
    public void setWorkStatus(WorkStatus workStatus){
        super.setWorkStatus(workStatus);
    }

    @Override
    public WorkStatus getWorkStatus(){
        return super.getWorkStatus();
    }

    @Override
    public void setWorkStage(WorkStage workStage){
        super.setWorkStage(workStage);
    }

    @Override
    public WorkStage getWorkStage(){
        return super.getWorkStage();
    }

    @Override
    public void setStartTime(Timestamp startTime) {
        super.setStartTime(startTime);
    }

    @Override
    public Timestamp getStartTime(){
        return super.getStartTime();
    }

    @Override
    public void setFinishTime(Timestamp finishTime) {
        super.setFinishTime(finishTime);
    }

    @Override
    public Timestamp getFinishTime(){
        return super.getFinishTime();
    }

    @Override
    public void setMilestoneWhenFinished(String milestoneWhenFinished){
        super.setMilestoneWhenFinished(milestoneWhenFinished);
    }

    public String getMilestoneWhenFinished(){
        return super.getMilestoneWhenFinished();
    }


}
