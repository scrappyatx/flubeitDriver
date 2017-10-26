/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.Driver;
import it.flube.driver.modelLayer.entities.batch.BatchHolder;

/**
 * Created on 8/16/2017
 * Project : Driver
 */

public interface DemoBatchInterface {


    BatchHolder createDemoBatch(Driver driver);

    BatchHolder createDemoBatch(Driver driver, String batchGuid);

}
