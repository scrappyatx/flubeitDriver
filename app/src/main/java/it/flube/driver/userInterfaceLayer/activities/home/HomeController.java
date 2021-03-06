/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.util.Log;

import it.flube.driver.dataLayer.AndroidDevice;
import timber.log.Timber;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class HomeController {
    private final String TAG = "HomeController";

    public HomeController() {
        Timber.tag(TAG).d( "created");
    }

    public void close(){
        Timber.tag(TAG).d("close");
    }

}
