/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.splashScreen;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.dataLayer.DeviceCheck;
import it.flube.driver.dataLayer.useCaseResponseHandlers.signInAndSignOut.SignInFromDeviceStorageResponseHandler;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.useCaseLayer.appInitialization.UseCaseThingsToDoWhenApplicationStarts;
import timber.log.Timber;

/**
 * Created by Bryan on 4/30/2017.
 */

public class SplashScreenController  {

    private final String TAG = "SplashScreenController";

    private ExecutorService useCaseExecutor;
    private MobileDeviceInterface device;
    private DeviceCheck deviceCheck;

    public SplashScreenController(Context applicationContext, AppCompatActivity activity) {
        useCaseExecutor = Executors.newSingleThreadExecutor();
        device = AndroidDevice.getInstance();
        deviceCheck = new DeviceCheck(applicationContext, activity);

        Timber.tag(TAG).d("created splash screen controlller");
    }

    public void doDeviceCheck(DeviceCheck.Response response) {
        deviceCheck.doDeviceCheck(response);
    }

    public void doStartupSequence() {
        Timber.tag(TAG).d("doStartupSequence START");
        useCaseExecutor.execute(new UseCaseThingsToDoWhenApplicationStarts(device, new SignInFromDeviceStorageResponseHandler()));
    }

    public void close() {
        deviceCheck.close();
        device = null;
        useCaseExecutor.shutdown();
    }


}