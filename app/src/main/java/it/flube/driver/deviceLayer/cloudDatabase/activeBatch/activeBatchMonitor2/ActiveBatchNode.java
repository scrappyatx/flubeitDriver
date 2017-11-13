/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.activeBatch.activeBatchMonitor2;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class ActiveBatchNode {
    private String batch;
    private Integer currentServiceOrderSequence;
    private Integer currentStepSequence;
    private CloudDatabaseInterface.ActionType actionType;
    private CloudDatabaseInterface.ActorType actorType;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getCurrentServiceOrderSequence() {
        return currentServiceOrderSequence;
    }

    public void setCurrentServiceOrderSequence(Integer currentServiceOrderSequence) {
        this.currentServiceOrderSequence = currentServiceOrderSequence;
    }

    public Integer getCurrentStepSequence() {
        return currentStepSequence;
    }

    public void setCurrentStepSequence(Integer currentStepSequence) {
        this.currentStepSequence = currentStepSequence;
    }

    public void setActionType(CloudDatabaseInterface.ActionType actionType) {
        this.actionType = actionType;
    }

    public void setActorType(CloudDatabaseInterface.ActorType actorType) {
        this.actorType = actorType;
    }

    Boolean hasAllBatchData() {
        return  ((batch != null) &&
                (currentServiceOrderSequence != null) &&
                (currentStepSequence != null));
    }

    Boolean hasBatchGuid(){
        return (batch != null);
    }

    String getBatchGuid() { return batch; }
    Integer getServiceOrderSequence() { return currentServiceOrderSequence; }
    Integer getStepSequence() { return currentStepSequence; }

    CloudDatabaseInterface.ActionType getActionType(){ return (actionType != null) ? actionType : CloudDatabaseInterface.ActionType.NOT_SPECIFIED;}
    CloudDatabaseInterface.ActorType getActorType() { return (actorType != null) ? actorType : CloudDatabaseInterface.ActorType.NOT_SPECIFIED; }

}
