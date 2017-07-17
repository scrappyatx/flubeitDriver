/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.interfaces;

import it.flube.driver.modelLayer.Batch;
import it.flube.driver.modelLayer.Driver;

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

    Boolean isDeveloperToolsMenuEnabled();

}
