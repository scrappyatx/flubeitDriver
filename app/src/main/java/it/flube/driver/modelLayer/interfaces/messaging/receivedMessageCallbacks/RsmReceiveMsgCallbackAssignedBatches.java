/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces.messaging.receivedMessageCallbacks;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.Batch;

/**
 * Created on 5/18/2017
 * Project : Driver
 */

public interface RsmReceiveMsgCallbackAssignedBatches {
    void receiveMsgAssignedBatches(ArrayList<Batch> batchList);
}
