/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.earnings.testEarnings;

import timber.log.Timber;

/**
 * Created on 6/26/2018
 * Project : Driver
 */
public class TestEarningsController {
    private static final String TAG="TestEarningsController";

    public TestEarningsController(){
        Timber.tag(TAG).d("created");
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }

}
