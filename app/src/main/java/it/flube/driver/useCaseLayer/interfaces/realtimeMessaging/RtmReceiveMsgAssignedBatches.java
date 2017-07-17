/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.interfaces.realtimeMessaging;

import java.util.ArrayList;

import it.flube.driver.modelLayer.Batch;

/**
 * Created on 5/18/2017
 * Project : Driver
 */

public interface RtmReceiveMsgAssignedBatches {
    void receiveMsgAssignedBatches(ArrayList<Batch> batchList);
}
