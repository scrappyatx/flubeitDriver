/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.interfaces;

/**
 * Created on 3/12/2018
 * Project : Driver
 */

public interface ActiveBatchManageInterface {
    public enum ActionType {
        BATCH_STARTED,
        ORDER_STARTED,
        STEP_STARTED,
        BATCH_WAITING_TO_FINISH,
        BATCH_FINISHED,
        BATCH_REMOVED,
        NO_BATCH,
        NOT_SPECIFIED
    }

    public enum ActorType {
        MOBILE_USER,
        SERVER_ADMIN,
        NOT_SPECIFIED
    }
}
