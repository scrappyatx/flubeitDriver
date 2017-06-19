/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.driverGoesOnDuty;

import it.flube.driver.modelLayer.entities.DriverSingleton;

/**
 * Created by Bryan on 4/25/2017.
 */

public class DriverGoesOnDuty implements Runnable {
    private static final String TAG = "DriverGoesOnDuty";

    private DriverGoesOnDutyResult mResult;

    public DriverGoesOnDuty(DriverGoesOnDutyResult result) {
        mResult = result;
    }

    public void run() {
        //DriverSingleton.getInstance().setOnDuty(true);
        mResult.driverGoesOnDutyEvent();
    }

}
