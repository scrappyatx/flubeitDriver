/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.driverGoesOffDuty;

import it.flube.driver.modelLayer.entities.DriverSingleton;


/**
 * Created by Bryan on 4/25/2017.
 */

public class DriverGoesOffDuty implements Runnable {
    private DriverGoesOffDutyResult mDriverGoesOffDutyResult;

    public DriverGoesOffDuty(DriverGoesOffDutyResult result) {
        mDriverGoesOffDutyResult = result;
    }

    public void run() {
        //DriverSingleton.getInstance().setOnDuty(false);
        mDriverGoesOffDutyResult.driverGoesOffDutyEvent();
    }
}
