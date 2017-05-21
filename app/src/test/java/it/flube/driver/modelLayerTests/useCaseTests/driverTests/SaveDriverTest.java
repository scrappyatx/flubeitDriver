/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayerTests.useCaseTests.driverTests;

import junit.framework.Assert;

import org.junit.Test;

import it.flube.driver.modelLayer.useCases.driver.storageUseCases.saveDriver.SaveDriverInfoCallback;
import it.flube.driver.modelLayerTests.mockData.mockStorage.MockDriverStorage;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class SaveDriverTest implements SaveDriverInfoCallback {


    @Test
    public void run() {
        MockDriverStorage mDriverStorage = new MockDriverStorage();
        //SaveDriver mSaveDriver = new SaveDriver(mDriverStorage, this);

       // mSaveDriver.run();
    }

    public void saveDriverInfoEvent(boolean result) {
       Assert.assertEquals("Save Driver Info", true, result);
    }

    public void saveDriverInfoSuccess() {

    }

    public void saveDriverInfoFailure(){

    }
}
