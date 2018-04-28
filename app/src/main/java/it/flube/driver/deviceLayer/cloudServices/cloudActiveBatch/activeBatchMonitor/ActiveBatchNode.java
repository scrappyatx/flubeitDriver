/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.activeBatchMonitor;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;

/**
 * Created on 11/7/2017
 * Project : Driver
 */

public class ActiveBatchNode {
    private String batch;
    private Integer currentServiceOrderSequence;
    private Integer currentStepSequence;
    private ActiveBatchManageInterface.ActionType actionType;
    private ActiveBatchManageInterface.ActorType actorType;

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

    public void setActionType(ActiveBatchManageInterface.ActionType actionType) {
        this.actionType = actionType;
    }

    public void setActorType(ActiveBatchManageInterface.ActorType actorType) {
        this.actorType = actorType;
    }

    public Boolean hasAllBatchData() {
        return  ((batch != null) &&
                (currentServiceOrderSequence != null) &&
                (currentStepSequence != null));
    }

    public Boolean hasBatchGuid(){
        return (batch != null);
    }

    public String getBatchGuid() { return batch; }
    public Integer getServiceOrderSequence() { return currentServiceOrderSequence; }
    public Integer getStepSequence() { return currentStepSequence; }

    public ActiveBatchManageInterface.ActionType getActionType(){ return (actionType != null) ? actionType : ActiveBatchManageInterface.ActionType.NOT_SPECIFIED;}
    public ActiveBatchManageInterface.ActorType getActorType() { return (actorType != null) ? actorType : ActiveBatchManageInterface.ActorType.NOT_SPECIFIED; }

}
