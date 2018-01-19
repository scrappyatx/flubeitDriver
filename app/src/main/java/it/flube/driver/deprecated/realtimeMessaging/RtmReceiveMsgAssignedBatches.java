/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deprecated.realtimeMessaging;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.batch.Batch;

/**
 * Created on 5/18/2017
 * Project : Driver
 */

public interface RtmReceiveMsgAssignedBatches {
    void receiveMsgAssignedBatches(ArrayList<Batch> batchList);
}
