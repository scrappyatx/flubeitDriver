/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.Offer;
import it.flube.driver.modelLayer.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.batch.BatchCloudDB;
import it.flube.driver.modelLayer.entities.Driver;

/**
 * Created on 8/16/2017
 * Project : Driver
 */

public interface DemoInterface {

    Offer createDemoOffer(Driver driver);

    Offer createDemoOffer(Driver driver, String offerGUID);

    Batch createDemoBatch(Driver driver);

    Batch createDemoBatch(Driver driver, String batchGUID);
}
