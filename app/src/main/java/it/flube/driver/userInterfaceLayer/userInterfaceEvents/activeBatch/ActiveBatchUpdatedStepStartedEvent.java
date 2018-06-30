/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch;

import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 6/21/2018
 * Project : Driver
 */
public class ActiveBatchUpdatedStepStartedEvent {
    private ActiveBatchManageInterface.ActorType actorType;
    private ActiveBatchManageInterface.ActionType actionType;
    private Boolean batchStarted;
    private Boolean orderStarted;
    private String batchGuid;
    private String serviceOrderGuid;
    private String stepGuid;
    private OrderStepInterface.TaskType taskType;

    public ActiveBatchUpdatedStepStartedEvent(ActiveBatchManageInterface.ActorType actorType, ActiveBatchManageInterface.ActionType actionType,
                                              Boolean batchStarted, Boolean orderStarted,
                                              String batchGuid, String serviceOrderGuid, String stepGuid, OrderStepInterface.TaskType taskType){
        this.actorType = actorType;
        this.actionType = actionType;
        this.batchStarted = batchStarted;
        this.orderStarted = orderStarted;
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.stepGuid = stepGuid;
        this.taskType = taskType;
    }

    public ActiveBatchManageInterface.ActorType getActorType() {
        return actorType;
    }

    public ActiveBatchManageInterface.ActionType getActionType() {
        return actionType;
    }

    public Boolean isBatchStarted() {
        return batchStarted;
    }

    public Boolean isOrderStarted() {
        return orderStarted;
    }

    public String getBatchGuid() {
        return batchGuid;
    }

    public String getServiceOrderGuid() {
        return serviceOrderGuid;
    }

    public String getStepGuid() {
        return stepGuid;
    }

    public OrderStepInterface.TaskType getTaskType() {
        return taskType;
    }
}
