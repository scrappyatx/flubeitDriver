/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.util.Log;

import it.flube.driver.dataLayer.AndroidDevice;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class HomeController {
    private final String TAG = "HomeNABController";

    public HomeController() {
        Log.d(TAG, "HomeController Controller CREATED");

    }

    public void sendSomeTestMessages() {
        //String clientId = AndroidDevice.getInstance().getUser().getDriver().getClientId();
        //AndroidDevice.getInstance().getRealtimeOfferMessages().attach(clientId);


    }

}