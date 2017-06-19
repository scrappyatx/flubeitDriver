/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayerTests.useCaseTests.driverTests;

import junit.framework.Assert;

import org.junit.Test;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.useCases.driver.storageUseCases.loadDriver.LoadDriverInfoResult;
import it.flube.driver.modelLayerTests.mockData.mockStorage.MockDriverStorage;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class LoadDriverTest implements LoadDriverInfoResult {

    @Test
    public void run() {
        DriverSingleton mDriver = DriverSingleton.getInstance();
        mDriver.setFirstName("");
        mDriver.setLastName("");
        mDriver.setClientId("");
        mDriver.setEmail("");
        //mDriver.setLoaded(false);
        //mDriver.setOnDuty(false);

        Assert.assertEquals("First Name","", mDriver.getFirstName());
        Assert.assertEquals("Last Name", "", mDriver.getLastName());
        Assert.assertEquals("Client Id", "", mDriver.getClientId());
        Assert.assertEquals("Email", "", mDriver.getEmail());
        //Assert.assertEquals("Loaded", false, mDriver.isLoaded());
        //Assert.assertEquals("On Duty", false, mDriver.isOnDuty());

        //initialize driver storage & use case
        MockDriverStorage mDriverStorage = new MockDriverStorage();
        //LoadDriver mLoadDriver = new LoadDriver(mDriverStorage, this);

        //run use case - uses info loaded from MockDriverStorage class
       // mLoadDriver.run();

        //check results
        Assert.assertEquals("First Name","Fizzi", mDriver.getFirstName());
        Assert.assertEquals("Last Name", "Battlecrank", mDriver.getLastName());
        Assert.assertEquals("Client Id", "12345", mDriver.getClientId());
        Assert.assertEquals("Email", "test@example.com", mDriver.getEmail());
        //Assert.assertEquals("Loaded", true, mDriver.isLoaded());
        //Assert.assertEquals("On Duty", false, mDriver.isOnDuty());

    }

    public void loadDriverInfoEvent(boolean result, DriverSingleton driver) {
        Assert.assertEquals("Load Result", true, result);
        Assert.assertNotNull(driver);
    }
}
