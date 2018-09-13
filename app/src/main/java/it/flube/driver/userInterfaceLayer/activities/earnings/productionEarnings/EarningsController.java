/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.earnings.productionEarnings;

import android.util.Log;

import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class EarningsController {
    private final String TAG = "EarningsController";

    public EarningsController() {
        Timber.tag(TAG).d("created");
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }
}
