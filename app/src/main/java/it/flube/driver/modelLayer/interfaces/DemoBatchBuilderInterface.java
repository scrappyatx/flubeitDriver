/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.entities.Driver;

/**
 * Created on 8/16/2017
 * Project : Driver
 */

public interface DemoBatchBuilderInterface {

    Batch getSimpleBatch(Driver driver, BatchCloudDB batch);
}
