/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.libbatchdata.entities.batch.Batch;
import it.flube.driver.modelLayer.entities.driver.Driver;

/**
 * Created on 6/24/2017
 * Project : Driver
 */

public interface AppUserInterface {

    void clear();

    void setDriver(Driver driver);

    Driver getDriver();

    Boolean isSignedIn();

    String getIdToken();

    void setIdToken(String idToken);

}
