/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayerTests.useCaseTests.driverTests;

import junit.framework.Assert;

import org.junit.Test;

import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.modelLayer.useCases.driver.driverGoesOffDuty.DriverGoesOffDutyResult;
import it.flube.driver.modelLayer.useCases.driver.driverGoesOnDuty.DriverGoesOnDutyResult;
import it.flube.driver.modelLayer.useCases.driver.driverGoesOffDuty.DriverGoesOffDuty;
import it.flube.driver.modelLayer.useCases.driver.driverGoesOnDuty.DriverGoesOnDuty;

/**
 * Created on 5/5/2017
 * Project : Driver
 */

public class DriverGoesOnAndOffDutyTest implements DriverGoesOffDutyResult, DriverGoesOnDutyResult {

    @Test
    public void run() {
        DriverGoesOffDuty mDriverGoesOffDuty = new DriverGoesOffDuty(this);
        DriverGoesOnDuty mDriverGoesOnDuty = new DriverGoesOnDuty(this);
        DriverSingleton mDriver = DriverSingleton.getInstance();

        Assert.assertNotNull(mDriver);

        mDriverGoesOnDuty.run();
        //Assert.assertEquals("On Duty", true, mDriver.isOnDuty());

        mDriverGoesOffDuty.run();
        //Assert.assertEquals("Off Duty", false, mDriver.isOnDuty());
    }


    public void driverGoesOnDutyEvent() {

    }

    public void driverGoesOffDutyEvent() {

    }
}
