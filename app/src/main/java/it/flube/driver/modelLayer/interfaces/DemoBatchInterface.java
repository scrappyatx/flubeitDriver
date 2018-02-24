/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import android.support.annotation.NonNull;

import it.flube.driver.modelLayer.builders.BuilderUtilities;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.entities.batch.BatchHolder;

/**
 * Created on 8/16/2017
 * Project : Driver
 */

public interface DemoBatchInterface {


    BatchHolder createDemoBatch(Driver driver);

    BatchHolder createDemoBatch(Driver driver, String batchGuid);

    BatchHolder createDemoVehiclePhotoBatch(@NonNull Driver driver);

    BatchHolder createDemoVehiclePhotoBatch(@NonNull Driver driver, @NonNull String batchGuid);

}
