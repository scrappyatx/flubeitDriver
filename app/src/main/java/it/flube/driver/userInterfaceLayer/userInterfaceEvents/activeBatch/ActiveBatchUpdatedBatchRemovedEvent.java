/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch;

import it.flube.libbatchdata.interfaces.ActiveBatchManageInterface;

/**
 * Created on 6/21/2018
 * Project : Driver
 */
public class ActiveBatchUpdatedBatchRemovedEvent {
    private ActiveBatchManageInterface.ActorType actorType;
    private String batchGuid;

    public ActiveBatchUpdatedBatchRemovedEvent(ActiveBatchManageInterface.ActorType actorType, String batchGuid){
        this.actorType = actorType;
        this.batchGuid = batchGuid;
    }

    public ActiveBatchManageInterface.ActorType getActorType() {
        return actorType;
    }

    public String getBatchGuid() {
        return batchGuid;
    }
}
