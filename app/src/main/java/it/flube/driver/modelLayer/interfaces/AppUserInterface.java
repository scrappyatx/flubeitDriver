/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.interfaces;

import it.flube.driver.modelLayer.entities.batch.Batch;
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

    void setSignedIn(Boolean signedIn);

    Boolean hasActiveBatch();

    Batch getActiveBatch();

    void setActiveBatch(Batch batch);

    void clearActiveBatch();

    String getIdToken();

    void setIdToken(String idToken);

}
