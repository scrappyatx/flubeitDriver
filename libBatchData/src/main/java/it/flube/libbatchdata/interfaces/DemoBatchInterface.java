/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.interfaces;


import it.flube.libbatchdata.entities.batch.BatchHolder;

/**
 * Created on 8/16/2017
 * Project : Driver
 */

public interface DemoBatchInterface {

    BatchHolder createDemoBatch(String clientId);

    BatchHolder createDemoBatch(String clientId, String batchGuid);

}
