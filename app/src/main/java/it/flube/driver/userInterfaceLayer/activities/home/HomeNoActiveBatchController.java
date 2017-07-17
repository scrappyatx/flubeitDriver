/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.home;

import android.os.Handler;
import android.util.Log;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.useCaseLayer.interfaces.MobileDeviceInterface;

/**
 * Created on 5/29/2017
 * Project : Driver
 */

public class HomeNoActiveBatchController {
    private final String TAG = "HomeNABController";

    public HomeNoActiveBatchController() {
        Log.d(TAG, "HomeNoActiveBatchController Controller CREATED");

    }

    public void sendSomeTestMessages() {
        String clientId = AndroidDevice.getInstance().getUser().getDriver().getClientId();
        AndroidDevice.getInstance().getRealtimeOfferMessages().connect(clientId);


    }

}
